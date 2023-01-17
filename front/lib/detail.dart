import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:karrot/main.dart';
import 'package:karrot/storage.dart';

import 'chat.dart';



class DetailPage extends StatefulWidget {

  final String id;
  final bool isSold;
  final bool isFavorite;

  const DetailPage({
    Key? key, required this.id, required this.isSold, required this.isFavorite,
  }) : super(key: key);

  @override
  State<DetailPage> createState() => _DetailPageState();
}

class _DetailPageState extends State<DetailPage> {
  late bool isFavorite;

  final categorymap = {
    "디지털기기": "DIGITAL",
    "생활가전": "HOUSEHOLD_APPLIANCES",
    "가구/인테리어": "FURNITURE",
    "유아동" : "CHILD",
    "생활/가공식품": "FOOD",
    "유아도서": "CHILD_BOOK",
    "스포츠/레저": "SPORTS",
    "여성잡화": "FEMALE_STUFF",
    "여성의류": "FEMALE_CLOTHES",
    "남성패션/잡화": "MALE_CLOTHES",
    "게임/취미": "GAME",
    "뷰티/미용": "BEAUTY",
    "반려동물용품":"PET",
    "도서/티켓/음반": "BOOK",
    "식물": "PLANTS",
    "기타 중고물품":"USED",
    "중고차": "USED_CAR"
  };
  late final reverseCategoryMap;

  @override
  void initState() {
    isFavorite = widget.isFavorite;
    reverseCategoryMap = Map.fromIterables(categorymap.values, categorymap.keys);
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _loadDetail(widget.id),
      builder: (context, snap) {
        return (snap.hasData) ? Scaffold(
            body: SingleChildScrollView(
              child: Column(
                children: [
                  Container(
                    height: 400,
                    color: Colors.grey[100],
                    child: Stack(
                      children: [
                        CarouselSlider(
                          options: CarouselOptions(height: 400.0),
                          items: List<int>.generate(snap.data?['img'].length, (i) => i).map((i) {
                            return Builder(
                              builder: (BuildContext context) {
                                return Container(
                                    width: MediaQuery.of(context).size.width,
                                    child: (snap.data['img'][0] == null) ? Container() :  Image.network(snap.data!['img'][i])
                                );
                              },
                            );
                          }).toList(),
                        ),
                        Padding(
                          padding: EdgeInsets.only(top: 20),
                          child: Row(
                            children: [
                              IconButton(
                                icon: Icon(Icons.arrow_back, size: 60),
                                onPressed: (){
                                  Navigator.pop(context);
                                },
                              ),
                              Padding(padding: EdgeInsets.symmetric(horizontal: 10)),
                              IconButton(
                                icon: Icon(Icons.home, size: 60),
                                onPressed: (){},
                              )
                            ]
                          ),
                        ),

                      ]
                    )
                  ),
                  Divider(height: 1, thickness: 2),
                  Container(
                    height: 100,
                    color: Colors.grey[100],
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Padding(padding: EdgeInsets.symmetric(horizontal: 12)),
                        Icon(Icons.account_circle_outlined, size: 80),
                        Padding(padding: EdgeInsets.symmetric(horizontal: 8)),
                        Text(snap.data!['nickname'] , style: TextStyle(fontSize: 24)),
                      ]
                    )
                  ),
                  Divider(height: 1, thickness: 2),
                  Container(
                    color: Colors.grey[100],
                      child: Padding(
                        padding: EdgeInsets.symmetric(vertical: 12, horizontal: 24),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                                children: [
                                  Expanded(
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          Row(
                                            children: [
                                              Text((widget.isSold)? "거래완료 ":"", style: TextStyle(fontSize:20, color: Colors.amber)),
                                              Text("제목\n" + snap.data!['title'] + '\n', style: TextStyle(fontSize: 20)),
                                            ],
                                          ),
                                          Container(),
                                          Text("카테고리\n" + reverseCategoryMap[snap.data!['stockCategory']] + '\n', style: TextStyle(fontSize: 20, color: Colors.grey)),
                                          Container(),
                                        ]
                                    ),
                                  ),
                                  Expanded(
                                    child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          Text(("가격\n"+ snap.data!['price'] + '\n'), style: TextStyle(fontSize: 20)),
                                          Container(),
                                          Text(("게시일자\n"+ snap.data!['createdAt'].substring(0, 10) + '\n'), style: TextStyle(fontSize: 20, color: Colors.grey)),
                                          Container()
                                        ]
                                    ),
                                  )
                                ]
                            ),
                            Text("내용\n"+snap.data!['contents']+'\n' , style: TextStyle(fontSize: 20)),
                            Container(),
                            Text("관심\n"+snap.data!['likes'].toString() +'\n', style: TextStyle(fontSize: 20, color: Colors.grey)),
                          ],
                        ),
                      )
                  ),
                  Divider(height: 1, thickness: 2),
                  Container(
                    color: Colors.grey[100],
                    child: Column(
                      children: [
                        Padding(
                          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                          child: Row(
                            children: [
                              Text("${snap.data!['nickname']}님의 판매 상품", style: TextStyle(fontSize: 20)),
                              Padding(padding: EdgeInsets.symmetric(horizontal: 48)),
                              Text("모두보기", style: TextStyle(fontSize: 18, color: Colors.grey)),
                            ]
                          ),
                        ),
                        Table(
                          // border: TableBorder.symmetric(inside: BorderSide(width: 1)),
                            columnWidths: const <int, TableColumnWidth>{
                              0: FlexColumnWidth(),
                              1: FlexColumnWidth(),
                            },
                            children: [
                              TableRow(
                                  children: <Widget>[
                                    (snap.data['otherProducts'].length > 0)? itemBoxBuilder(snap, 0): Container(),
                                    (snap.data['otherProducts'].length > 1)? itemBoxBuilder(snap, 1): Container(),
                                  ]
                              )
                            ]
                        ),
                      ],
                    )

                  ),
                  Divider(height: 1, thickness: 2),
                  Container(
                    height: 100,
                    color: Colors.grey[100],
                    child: Center(
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Stack(
                          children: [Row(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              IconButton(
                                icon: (isFavorite) ? Icon(Icons.favorite , size: 40, color: Colors.red): Icon(Icons.favorite_border , size: 40, color: Colors.black),
                                onPressed: () async {
                                  if(isFavorite) {
                                    await _reqUnFavorite(widget.id);
                                  } else {
                                    await _reqFavorite(widget.id);
                                  }
                                  setState(() {
                                    isFavorite = !isFavorite;
                                  }
                                 );
                                },
                              ),
                              VerticalDivider(width: 40, thickness: 2),
                              Text(snap.data['price'] ,style: TextStyle(fontSize: 16)),
                            ]
                          ),Row(
                            mainAxisAlignment: MainAxisAlignment.end,
                              children: [
                                Center(
                                  child: SizedBox(
                                    height: 52,
                                    child: ElevatedButton(
                                      style: ElevatedButton.styleFrom(
                                        primary: Colors.orangeAccent,
                                      ),
                                      onPressed: () async {
                                        await Navigator.push(
                                          context,
                                          MaterialPageRoute(builder: (context) => ChatPage()),
                                        );

                                        setState(() {

                                        });
                                      },
                                      child: Text('채팅하기', style: TextStyle(color: Colors.white, fontSize: 14)),
                                    ),
                                  ),
                                ),
                              ]
                          ),]
                        ),
                      ),
                    )
                  )
                ],
              ),
            ),
        ): Container(color: Colors.white);}
      );
  }

  Widget itemBoxBuilder(snap, index) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 24.0, vertical: 12.0),
      child: Container(
        height: 150,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Padding(
              padding: const EdgeInsets.only(bottom: 10),
              child: Container(
                height: 100,
                color:Colors.grey
              ),
            ),
            Text(snap.data['otherProducts'][index]['title']),
            Text(snap.data['otherProducts'][index]['price'])
          ]
        )
      ),
    );
  }

  Future<dynamic> _reqFavorite(id) async {
    final String? token = await storage.read(key: "accessToken");


    var url = Uri.http(localhost(), '/api/posts/${id}/like');
    var response = await http.put(
      url,
      headers: {
        'Authorization': 'Bearer $token'
      },
    );
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;

    print(decodedResponse);
  }

  Future<dynamic> _reqUnFavorite(id) async {
    final String? token = await storage.read(key: "accessToken");


    var url = Uri.http(localhost(), '/api/posts/${id}/unlike');
    var response = await http.put(
      url,
      headers: {
        'Authorization': 'Bearer $token'
      },
    );
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;

    print(decodedResponse);
  }

  Future<dynamic> _loadDetail(id) async {
    final String? token = await storage.read(key: "accessToken");


    var url = Uri.http(localhost(), '/api/posts/${id}');
    var response = await http.get(
      url,
      headers: {
        'Authorization': 'Bearer $token'
      },
    );
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;

    print(decodedResponse);
    if(decodedResponse['message'] == "상품 상세 페이지 찾기에 성공하였습니다.") {
      return decodedResponse['data'];
    }
    else {
      return {
        "img": [
          null
        ],
        "imgSize": 0,
        "nickname": "닉네임",
        "title": "존재하지 않습니다.",
        "price": "가격",
        "stockCategory": "카테고리",
        "contents": "내용",
        "createdAt": "0000-00-00",
        "likes": "관심",
        "otherProducts": [
          {
            "img": null,
            "title": "제목",
            "price": "가격"
          },
          {
            "img": null,
            "title": "제목",
            "price": "가격"
          }
        ]
      };
    }
  }
}
