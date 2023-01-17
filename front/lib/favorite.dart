import 'package:flutter/material.dart';

import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:karrot/storage.dart';
import 'package:karrot/detail.dart';
import 'package:karrot/main.dart';


class FavoritePage extends StatefulWidget {

  static const List<Tab> myTabs = <Tab> [
    Tab(text: "전체"),
    Tab(text: "판매중"),
    Tab(text: "거래완료"),
  ];

  const FavoritePage({
    Key? key,
  }) : super(key: key);

  @override
  State<FavoritePage> createState() => _FavoritePageState();
}

class _FavoritePageState extends State<FavoritePage> {

  int _selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _loadItemList(0),
      builder: (context, snap) { return DefaultTabController(
        length: 3,
        child: Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: false,
            elevation: 0,
            backgroundColor: Colors.white,
            toolbarHeight: 80,
            title: Padding(
              padding: const EdgeInsets.all(12),
              child: Row(
                  children: [
                    Padding(
                        padding: EdgeInsets.only(right: 20),
                        child: IconButton(
                          icon: Icon(Icons.arrow_back, size: 32),
                          onPressed: (){
                            Navigator.pop(context);
                          },
                        )
                    ),
                    Padding(
                      padding: EdgeInsets.only(right: 24),
                      child: Text(
                        "관심목록",
                        style: TextStyle(fontSize: 32),
                      ),
                    ),
                  ]
              ),
            ),
            bottom: PreferredSize(
              preferredSize: Size(double.infinity, 70),
              child: TabBar(tabs: FavoritePage.myTabs,
                indicator: UnderlineTabIndicator(
                  borderSide: BorderSide(width: 3.0, color: Colors.grey),
                ),
                labelStyle: TextStyle(fontSize: 24),
              ),
            ),
          ),
          body: TabBarView(
            children : FavoritePage.myTabs.map ((Tab tab) {
              final int tabIndex = ((tab.text == "전체") ? 0 : ((tab.text == "판매중") ? 1 : 2));
              return Center(
                child: Column(
                  children: [
                    Divider(height:2, thickness: 2),
                    Expanded(
                      child: Padding(
                        padding: const EdgeInsets.all(0),
                        child: FutureBuilder(
                            future: _loadItemList(0),
                            builder: (context, snap) {
                              return (snap.hasData) ? Stack(
                                  children: [ListView.separated(
                                    padding: const EdgeInsets.all(0),
                                    itemCount: snap.data!.length,
                                    itemBuilder: (BuildContext context, index) {
                                      if(tabIndex == 0 || (tabIndex == 1 && snap.data[index]['stockStatus'] == 'SELLING') || (tabIndex == 2 && snap.data[index]['stockStatus'] == 'SOLD'))
                                      return TextButton(
                                        onPressed: () async {
                                          print(snap.data![index]['stockStatus'] == "SOLD");
                                          await Navigator.push(
                                            context,
                                            MaterialPageRoute(builder: (context) => DetailPage(id: snap.data![index]['postId'], isSold: (snap.data![index]['stockStatus'] == 'SOLD'), isFavorite: true)),
                                          );
                                          setState((){

                                          });
                                        },
                                        child: Padding(
                                          padding: EdgeInsets.all(16),
                                          child: Container(
                                              height: 120,
                                              child: Stack(
                                                  children: [
                                                    Row(
                                                        children: [
                                                          Container(
                                                              width: 120,
                                                              height: 120,
                                                              child: Padding(
                                                                padding: const EdgeInsets.all(8.0),
                                                                child: Container(
                                                                    child: (snap.data[index]['url'] != null) ? Image.network(snap.data[index]['url']): Container()
                                                                ),
                                                              )
                                                          ),
                                                          Padding(padding: EdgeInsets.symmetric(horizontal: 16)),
                                                          Column(
                                                              crossAxisAlignment: CrossAxisAlignment.start,
                                                              children: [
                                                                Row(
                                                                  children: [
                                                                    // Container(
                                                                    //   width: 60,
                                                                    //   height: 30,
                                                                    //   decoration: BoxDecoration(
                                                                    //       color: Colors.black,
                                                                    //       borderRadius: BorderRadius.circular(3 )
                                                                    //   ),
                                                                    //   child: Center(child: Text("거래완료", style: TextStyle(color: Colors.white))),
                                                                    // ),
                                                                    // Padding(padding: EdgeInsets.only(left:8)),
                                                                    Text(snap.data![index]['title'], style: TextStyle(fontSize: 20, color:Colors.black)),
                                                                  ],
                                                                ),
                                                                Padding(padding: EdgeInsets.only(top: 4)),
                                                                Text("장소", style: TextStyle(fontSize: 18, color: Colors.grey)),
                                                                Padding(padding: EdgeInsets.only(top: 4)),
                                                                Text(snap.data![index]['price'], style: TextStyle(fontSize: 18, color:Colors.black)),
                                                              ]
                                                          ),
                                                        ]
                                                    ),
                                                    Row(
                                                      mainAxisAlignment: MainAxisAlignment.end,
                                                      children: [
                                                        Column(
                                                          children: [
                                                            Padding(padding: EdgeInsets.only(top: 40)),
                                                            if(snap.data![index]['stockStatus'] == "SOLD") Container(
                                                              width: 60,
                                                              height: 30,
                                                              decoration: BoxDecoration(
                                                                  color: Colors.black,
                                                                  borderRadius: BorderRadius.circular(3 )
                                                              ),
                                                              child: Center(child: Text("거래완료", style: TextStyle(color: Colors.white))),
                                                            ),
                                                            if(snap.data![index]['likes'] != 0) Container(
                                                                width: 90,
                                                                height: 30,
                                                                child: Row(
                                                                    mainAxisAlignment: MainAxisAlignment.center,
                                                                    children: [
                                                                      Padding(
                                                                        padding: const EdgeInsets.all(0),
                                                                        child: IconButton(
                                                                          icon: (true) ? Icon(Icons.favorite , size: 24, color: Colors.red): Icon(Icons.favorite_border , size: 40, color: Colors.black),
                                                                          onPressed: () async {
                                                                            if(true) {
                                                                              await _reqUnFavorite(snap.data[index]['postId']);
                                                                            } else {
                                                                              await _reqFavorite(snap.data[index]['id']);
                                                                            }
                                                                            setState(() {
                                                                            }
                                                                            );
                                                                          },
                                                                        ),
                                                                      ),
                                                                      Padding(
                                                                        padding: const EdgeInsets.only(top: 10),
                                                                        child: Center(child: Text(snap.data![index]['likes'].toString(), style: TextStyle(fontSize: 16, color:Colors.black))),
                                                                      )
                                                                    ]
                                                                )
                                                            ),
                                                          ],
                                                        ),
                                                      ],
                                                    ),
                                                  ]
                                              )
                                          ),
                                        ),
                                      ); else return Container();
                                    },
                                    separatorBuilder: (BuildContext context, int index) => const Divider(height: 1, thickness: 2),
                                  ),
                                  ]
                              ) : Container(color: Colors.black);}
                        ),
                      ),
                    )
                  ],
                ),
              );},
          ).toList()),
        ),
      );},
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

  Future<dynamic> _loadItemList(int category) async {
    final String? token = await storage.read(key: "accessToken");
    // print(token);
    var url = Uri.http(localhost(), '/api/mypages/likes');
    var response = await http.get(
      url,
      headers: {
        'Authorization': 'Bearer $token'
      },
    );
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
    print(decodedResponse['data']['simpleProductList']['content']);
    return decodedResponse['data']['simpleProductList']['content'];
  }
}

