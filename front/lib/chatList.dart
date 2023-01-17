import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:karrot/storage.dart';

import 'main.dart';

import 'package:http/http.dart' as http;

class ChatListPage extends StatefulWidget {

  const ChatListPage({
    Key? key,
  }) : super(key: key);

  @override
  State<ChatListPage> createState() => _ChatListPageState();
}

class _ChatListPageState extends State<ChatListPage> {

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _loadItemList(0),
      builder: (context, snap){ return (snap.hasData) ? Scaffold(
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
                      "채팅목록",
                      style: TextStyle(fontSize: 32),
                    ),
                  ),
                ]
            ),
          ),
        ),
        body: Center(
          child: Column(
            children: [
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.all(0),
                  child: ListView.separated(
                    padding: const EdgeInsets.all(0),
                    itemCount: 10, // fix this
                    itemBuilder: (BuildContext context, index) {
                      return Padding(
                        padding: EdgeInsets.all(16),
                        child: Container(
                            height: 160,
                            child: Row(
                                children: [
                                  Container(
                                    width: 120,
                                    height: 140,
                                    child: Column(
                                      children: [
                                        Container(
                                            width: 100,
                                            height: 100,
                                            child: Icon(Icons.account_circle_outlined, size: 100)
                                        ),
                                        Container(
                                          width: 120,
                                          child: Text(snap.data[index][''], textAlign: TextAlign.center, style: TextStyle(fontSize: 24)),
                                        )
                                      ]
                                    )
                                  ),
                                  Padding(padding: EdgeInsets.symmetric(horizontal: 16)),
                                  Expanded(
                                    child: Container(
                                      child: Text(snap.data[index][''], style: TextStyle(fontSize: 18, color: Colors.grey))
                                    ),
                                  ),
                                  Column(
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.only(top:16.0),
                                        child: Container(
                                            width: 60,
                                            height: 60,
                                          color: Colors.amber
                                        ),
                                      ),
                                      Padding(padding: EdgeInsets.symmetric(vertical: 25)),
                                      if(snap.data[index]['']) Container(
                                        width: 60,
                                        height: 30,
                                        decoration: BoxDecoration(
                                            color: Colors.black,
                                            borderRadius: BorderRadius.circular(3 )
                                        ),
                                        child: Center(child: Text("거래완료", style: TextStyle(color: Colors.white))),
                                      ),
                                    ],
                                  ),
                                ]
                            )
                        ),
                      );
                    },
                    separatorBuilder: (BuildContext context, int index) => const Divider(height: 1, thickness: 2),
                  ),
                ),
              )
            ],
          ),

        ),

      ) : Container(); },
    );
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
}
