import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:karrot/chatList.dart';
import 'dart:convert';

import 'package:karrot/search.dart';
import 'package:karrot/category.dart';
import 'package:karrot/storage.dart';
import 'package:karrot/write.dart';
import 'package:karrot/detail.dart';
import 'package:karrot/profile.dart';
import 'package:karrot/userSellList.dart';
import 'package:karrot/favorite.dart';
import 'package:karrot/sellChatList.dart';
import 'package:karrot/main.dart';


import 'main.dart';


String path(str) {
  return (kIsWeb) ? 'assets/$str' : str;
}

// class Item {
//   String imgPath;
//   String title;
//   String location;
//   int price;
//   int favorite;
//   Item(this.imgPath, this.title, this.location, this.price, this.favorite);
// }

class DevTownPage extends StatefulWidget {

  const DevTownPage({
    Key? key,
  }) : super(key: key);

  @override
  State<DevTownPage> createState() => _DevTownPageState();
}

class _DevTownPageState extends State<DevTownPage> {

  int _selectedIndex = 0;

  int category = -1;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        toolbarHeight: 80,
        elevation: 0,
        backgroundColor: Colors.white,
        title: Padding(
          padding: const EdgeInsets.all(12),
          child: Row(
              children: [
                Padding(
                  padding: EdgeInsets.only(right: 24),
                  child: (_selectedIndex == 0) ? Text(
                    "전농동",
                    style: TextStyle(fontSize: 32)
                  ) :
                  Text(
                    "나의 당근",
                    style: TextStyle(fontSize: 32)
                  ),
                ),
                Padding(
                    padding: (_selectedIndex == 0) ? EdgeInsets.only(left: 140) :  EdgeInsets.only(left: 100),
                    child: IconButton(
                      icon: Icon(Icons.search, size: 32),
                      onPressed: () async {await Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => SearchPage()),
                      );},
                    )
                ),
                Padding(
                    padding: EdgeInsets.zero,
                    child: IconButton(
                      icon: Icon(Icons.dehaze, size: 32),
                      onPressed: () async {category = await Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => CategoryPage()),
                      );}
                    )
                ),
              ]
          ),
        ),
      ),
      body: (_selectedIndex == 0) ? Center(
        child: Column(
          children: [
            Divider(height:2, thickness: 2),
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(0),
                child: FutureBuilder(
                  future: _loadItemList(category),
                  builder: (context, snap) {
                      return (snap.hasData) ? Stack(
                        children: [ListView.separated(
                        padding: const EdgeInsets.all(0),
                        itemCount: snap.data!.length,
                        itemBuilder: (BuildContext context, index) {
                          return TextButton(
                            onPressed: () async {
                              print(snap.data![index]['stockStatus'] == "SELLING");
                              await Navigator.push(
                                context,
                                MaterialPageRoute(builder: (context) => DetailPage(id: snap.data![index]['postId'], isSold: (snap.data![index]['stockStatus'] == 'SOLD'), isFavorite: snap.data[index]['isLiked'])),
                              );
                              setState((){});
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
                                        if(snap.data![index]['likes'] != 0) Row(
                                          mainAxisAlignment: MainAxisAlignment.end,
                                          children: [
                                            Column(
                                              children: [
                                                Padding(padding: EdgeInsets.only(top: 40)),
                                                Container(
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
                            ),
                          );
                        },
                        separatorBuilder: (BuildContext context, int index) => const Divider(height: 1, thickness: 2),
                    ),
                        Padding(
                          padding: const EdgeInsets.only(top: 560, left:320),
                          child: Container(
                            width: 80,
                            height: 80,
                            child: FloatingActionButton(
                              onPressed: () async {
                                await Navigator.push(
                                  context,
                                  MaterialPageRoute(builder: (context) => WritePage(id: "")),
                                );
                                setState(() {

                                });

                              },
                              child: Icon(Icons.add, size: 64, color: Colors.white),
                            ),
                          ),
                        )
                        ]
                      ) : Container(color: Colors.black);}
                ),
              ),
            )
          ],
        ),
      ):
      Center(
        child: FutureBuilder(
          future: _loadUserInfo(),
            builder:(context, snap) {
              return (snap.hasData) ? Column(
                children: [
                  Divider(height: 2, thickness: 2),
                  Container(
                    width: double.infinity,
                    height: 150,
                    child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Icon(Icons.account_circle_outlined, size: 100),
                          Padding(padding: EdgeInsets.only(left: 8)),
                          Text("${snap.data}", style: TextStyle(fontSize: 36)),
                        ]
                    ),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => ProfilePage(nickname: snap.data)),
                      );
                    },
                    child: Container(
                        width: double.infinity,
                        decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(8),
                            border: Border.all(width: 3)
                        ),
                        child: Padding(
                          padding: const EdgeInsets.all(4.0),
                          child: Text('프로필 수정', textAlign: TextAlign.center,
                              style: TextStyle(color: Colors.black,
                                  fontSize: 36)),
                        )
                    ),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => UserSellListPage()),
                      );
                    },
                    child: Container(
                        width: double.infinity,
                        child: Padding(
                          padding: const EdgeInsets.all(24.0),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Icon(Icons.library_books, size: 54),
                              Padding(padding: EdgeInsets.symmetric(
                                  horizontal: 48)),
                              Text('판매내역', textAlign: TextAlign.center,
                                  style: TextStyle(
                                      color: Colors.black, fontSize: 36)),
                            ],
                          ),
                        )
                    ),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => FavoritePage()),
                      );
                    },
                    child: Container(
                        width: double.infinity,
                        child: Padding(
                          padding: const EdgeInsets.all(24.0),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Icon(Icons.favorite_border, size: 54),
                              Padding(padding: EdgeInsets.symmetric(
                                  horizontal: 48)),
                              Text('관심목록', textAlign: TextAlign.center,
                                  style: TextStyle(
                                      color: Colors.black, fontSize: 36)),
                            ],
                          ),
                        )
                    ),
                  ),
                  TextButton(
                    onPressed: () async {
                      await Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => ChatListPage()),
                      );

                      setState(() {

                      });
                    },
                    child: Container(
                        width: double.infinity,
                        child: Padding(
                          padding: const EdgeInsets.all(24.0),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Icon(Icons.chat, size: 54),
                              Padding(padding: EdgeInsets.symmetric(
                                  horizontal: 48)),
                              Text('채팅목록', textAlign: TextAlign.center,
                                  style: TextStyle(
                                      color: Colors.black, fontSize: 36)),
                            ],
                          ),
                        )
                    ),
                  ),
                  TextButton(
                  onPressed: () async {await _logout();
                    Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(builder: (context) => MainPage()),
                    );},
                    child: Container(
                        width: double.infinity,
                        child: Padding(
                          padding: const EdgeInsets.all(0),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Text('Log out', textAlign: TextAlign.center,
                                  style: TextStyle(
                                      color: Colors.black, fontSize: 36)),
                            ],
                          ),
                        )
                    ),
                  ),
                ],
              ): Container();
            }
        ),
      ),
      bottomNavigationBar:  SizedBox(
        height: 100,
        child: BottomNavigationBar(items: const <BottomNavigationBarItem>[
            BottomNavigationBarItem(
              icon: Icon(Icons.home, size: 36),
              label: '홈',

            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.person, size: 36),
              label: '나의 당근',
            ),
          ],
          selectedFontSize: 20,
          unselectedFontSize: 18,
          currentIndex: _selectedIndex,
          onTap: (index) {
            setState(
                () {
                  _selectedIndex = index;
                }
            );
          }
        ),
      )
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
    var url = Uri.http(localhost(), '/api/posts');
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

  Future<String> _loadUserInfo() async {
    final String? token = await storage.read(key: "accessToken");

    var url = Uri.http(localhost(), '/api/mypages');
    var response = await http.get(
      url,
      headers: {
        'Authorization': 'Bearer $token'
      },
    );
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
    // await storage.write(key: "nickname", value: decodedResponse['data']['nickname']);
    if(decodedResponse['message'] == "마이 페이지 불러오기에 성공하였습니다.")
      return decodedResponse['data']['nickname'];
    else
      return "";
  }

  Future<void> _logout() async {
    final String? atoken = await storage.read(key: "accessToken");
    final String? rtoken = await storage.read(key: "refreshToken");

    var url = Uri.http(localhost(), '/api/auth/logout');
    var response = await http.post(
        url,
        headers: {
          'Authorization': 'Bearer $atoken',
          "Content-Type": "application/json",
        },
    );

    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
    print(decodedResponse);
  }
}
