import 'package:flutter/material.dart';

import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:karrot/storage.dart';
import 'package:karrot/main.dart';

class ProfilePage extends StatefulWidget {

  final nickname;

  const ProfilePage({
    Key? key, this.nickname,
  }) : super(key: key);

  @override
  State<ProfilePage> createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {

  final controller = TextEditingController();


  @override
  void initState() {
    controller.addListener((){
      setState(() {

      });
    });
    super.initState();
  }

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
                    child: Text(
                      "나의 당근",
                      style: TextStyle(fontSize: 32),
                    ),
                  ),
                  Padding(
                      padding: EdgeInsets.only(left: 100),
                      child: IconButton(
                        icon: Icon(Icons.search, size: 32),
                        onPressed: (){},
                      )
                  ),
                  Padding(
                      padding: EdgeInsets.zero,
                      child: IconButton(
                        icon: Icon(Icons.dehaze, size: 32),
                        onPressed: (){},
                      )
                  ),
                ]
            ),
          ),
        ),
        body: Center(
          child: Column(
            children: [
              Divider(height:2, thickness: 2),
              Container(
                width: double.infinity,
                height: 200,
                child: Stack(
                    children: [
                      Center(child: Icon(Icons.account_circle_outlined, size: 200)),
                      Padding(
                        padding: EdgeInsets.only(left: 240, top: 130),
                        child: Container(
                          width: 56,
                          height: 56,
                          child: FloatingActionButton(
                              backgroundColor: Colors.white,
                              elevation: 0,
                              child: Icon(Icons.camera_alt_outlined, size: 56),
                              onPressed: (){showDialog(context: context, builder: (context) {
                                return Expanded(
                                  child: Column(
                                    mainAxisAlignment: MainAxisAlignment.end,
                                    children: [
                                      Container(
                                        width: double.infinity,
                                        height: 140,
                                        color: Colors.white,
                                        child: Column(
                                          children: [
                                            TextButton(
                                              onPressed:() {},
                                              child: Container(
                                                child: Padding(
                                                  padding: const EdgeInsets.symmetric(horizontal: 24, vertical:0.0),
                                                  child: Row(
                                                    children: [
                                                      Icon(Icons.photo_library_outlined, size: 48, color: Colors.black),
                                                      Padding(padding: EdgeInsets.symmetric(horizontal: 12)),
                                                      Text('앨범에서 선택', style: TextStyle(color: Colors.black, fontSize: 24)),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                            ),
                                            TextButton(
                                              onPressed:() {},
                                              child: Container(
                                                child: Padding(
                                                  padding: const EdgeInsets.symmetric(horizontal: 24, vertical:0),
                                                  child: Row(
                                                    children: [
                                                      Icon(Icons.remove_circle_outline, size: 48, color: Colors.black),
                                                      Padding(padding: EdgeInsets.symmetric(horizontal: 12)),
                                                      Text('프로필 사진 삭제', style: TextStyle(color: Colors.black, fontSize: 24)),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                            ),
                                          ]
                                        )
                                      )
                                    ]
                                  ),
                                );
                              });}
                          ),
                        ),
                      ),
                    ]
                ),
              ),
              Container(
                  width: double.infinity,
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(8),
                      border: Border.all(width: 3)
                  ),
                  child: TextField(
                    controller: controller,
                    textAlign: TextAlign.center,
                    style: TextStyle(fontSize: 36),
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: widget.nickname,
                      hintStyle: TextStyle(color: Colors.black, fontSize: 36),
                    )
                  )
              ),
              Expanded(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    TextButton(
                      onPressed: () async{
                        print(controller.text);
                        await _fixUserInfo(controller.text);
                        Navigator.pop(context);
                      },
                      child: Container(
                          width: double.infinity,
                          decoration: BoxDecoration(
                            color: (controller.text == "") ? Colors.grey[400] : Colors.amber,
                              borderRadius: BorderRadius.circular(8),
                          ),
                          child: Text('완료', textAlign: TextAlign.center, style: TextStyle(color: Colors.white, fontSize: 36))
                      ),
                    ),
                  ],
                ),
              ),
            ],
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
          ),
        )
    );
  }

  Future<void> _fixUserInfo(newName) async {
    final String? token = await storage.read(key: "accessToken");

    var url = Uri.http(localhost(), '/api/mypages');
    var response = await http.put(
      url,
      headers: {
        'Authorization': 'Bearer $token',
        "Content-Type": "application/json",
      },
      body: jsonEncode({
        "nickName": newName,
        "name":"",
        "phone":"",
        "region":"",
        "city":"",
        "dong":"",
        "introduction":""
      })
    );

    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
    print(decodedResponse);
  }
}
