import 'package:flutter/material.dart';
import 'package:karrot/main.dart';

class SellChatListPage extends StatefulWidget {

  const SellChatListPage({
    Key? key,
  }) : super(key: key);

  @override
  State<SellChatListPage> createState() => _SellChatListPageState();
}

class _SellChatListPageState extends State<SellChatListPage> {

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
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

                        },
                      )
                  ),
                  Padding(
                    padding: EdgeInsets.only(right: 24),
                    child: Text(
                      "이 상품 관련 채팅 목록",
                      style: TextStyle(fontSize: 28),
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
                                              child: Text("닉네임", textAlign: TextAlign.center, style: TextStyle(fontSize: 24)),
                                            )
                                          ]
                                      )
                                  ),
                                  Padding(padding: EdgeInsets.symmetric(horizontal: 16)),
                                  Expanded(
                                    child: Container(
                                        child: Text("마지막 채팅 내용", style: TextStyle(fontSize: 18, color: Colors.grey))
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

      ),
    );
  }
}
