import 'package:flutter/material.dart';

import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:karrot/storage.dart';
import 'package:karrot/userSellDetail.dart';
import 'package:karrot/main.dart';

const status = <String>['SELLING', 'RESERVED', 'SOLD']; 

class UserSellListPage extends StatefulWidget {

  static const List<Tab> myTabs = <Tab> [
    Tab(text: "전체"),
    Tab(text: "거래완료"),
  ];

  const UserSellListPage({
    Key? key,
  }) : super(key: key);

  @override
  State<UserSellListPage> createState() => _UserSellListPageState();
}

class _UserSellListPageState extends State<UserSellListPage> {

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _loadItemList(0),
      builder: (context, snap) { return DefaultTabController(
        length: 2,
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
                        "판매내역",
                        style: TextStyle(fontSize: 32),
                      ),
                    ),
                  ]
              ),
            ),
            bottom: PreferredSize(
              preferredSize: Size(double.infinity, 70),
              child: TabBar(tabs: UserSellListPage.myTabs,
                indicator: UnderlineTabIndicator(
                  borderSide: BorderSide(width: 3.0, color: Colors.grey),
                ),
                labelStyle: TextStyle(fontSize: 24),
              ),
            ),
          ),
          body: TabBarView(
              children : UserSellListPage.myTabs.map ((Tab tab) {
                final int tabIndex = (tab.text == "전체") ? 0 : 1 ;
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
                                        if(tabIndex == 0 || (tabIndex == 1 && snap.data[index]['stockStatus'] == 'SOLD'))
                                          return TextButton(
                                            onPressed: () async {
                                              print(snap.data![index]['stockStatus'] == "SELLING");
                                              await Navigator.push(
                                                context,
                                                MaterialPageRoute(builder: (context) => UserSellDetailPage(id: snap.data![index]['postId'], isSelling: (status.indexOf(snap.data[index]['stockStatus'])) , isFavorite: false)),
                                              );
                                              setState(() {

                                              });
                                            },
                                            child: Padding(
                                              padding: EdgeInsets.all(16),
                                              child: Column(
                                                children: [
                                                  Container(
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
                                                                            color: Colors.grey[400],
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
                                                                                  icon: (snap.data[index]['isLiked']) ? Icon(Icons.favorite , size: 24, color: Colors.red): Icon(Icons.favorite_border , size: 24, color: Colors.black),
                                                                                  onPressed: () async {
                                                                                    if(snap.data[index]['isLiked']) {
                                                                                      await _reqUnFavorite(snap.data[index]['postId']);
                                                                                    } else {
                                                                                      await _reqFavorite(snap.data[index]['postId']);
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
                                                  Padding(
                                                    padding: const EdgeInsets.only(top: 16.0),
                                                    child: Divider(height: 2, thickness: 2),
                                                  ),
                                                  Row(
                                                      children: [
                                                        Expanded(child: TextButton(
                                                          onPressed: () async {
                                                            await _reqReserved(snap.data[index]['postId']);
                                                            setState(() {

                                                            });
                                                          },
                                                          child: Container(
                                                              child: Center(child: Text("예약중으로 변경", style: TextStyle(color: Colors.black, fontSize: 24)))
                                                          ),
                                                        )),
                                                        Expanded(child: TextButton(
                                                          onPressed: () async {
                                                            await _reqSold(snap.data[index]['postId']);
                                                            setState(() {

                                                            });
                                                          },
                                                          child: Container(
                                                              child: Center(child: Text("거래완료로 변경", style: TextStyle(color: Colors.black, fontSize: 24)))
                                                          ),
                                                        )),
                                                      ]
                                                  )
                                                ],
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

  Future<dynamic> _reqReserved(id) async {

    final String? token = await storage.read(key: "accessToken");
    // print(token);
    print(id);
    var url = Uri.http(localhost(), '/api/posts/${id}/reserved');
    var response = await http.put(
      url,
      headers: {
        'Authorization': 'Bearer $token',
      },
    );

    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
    print(decodedResponse);
  }

  Future<dynamic> _reqSold(id) async {

    final String? token = await storage.read(key: "accessToken");
    // print(token);
    var url = Uri.http(localhost(), '/api/posts/${id}/sold');
    var response = await http.put(
      url,
      headers: {
        'Authorization': 'Bearer $token',
      },
    );

    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
    print(decodedResponse);
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
    var url = Uri.http(localhost(), '/api/mypages/stock');
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

