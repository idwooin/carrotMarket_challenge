import 'package:flutter/material.dart';
import 'package:karrot/main.dart';

class ChatPage extends StatefulWidget {

  const ChatPage({
    Key? key,
  }) : super(key: key);

  @override
  State<ChatPage> createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        toolbarHeight: 80,
        elevation: 0,
        backgroundColor: Colors.white,
        title: Padding(
          padding: const EdgeInsets.all(12),
          child: Row(
              children: [
                Padding(
                    padding: EdgeInsets.only(right: 4),
                    child: IconButton(
                      icon: Icon(Icons.arrow_back, size: 32),
                      onPressed: (){},
                    )
                ),
                Expanded(
                  child: SizedBox(
                    height: 48,
                    child: Padding(
                        padding: EdgeInsets.only(),
                        child: Center(
                          child: Text(
                            "중고거래 글쓰기",
                            style: TextStyle(fontSize: 24),
                          ),
                        )
                    ),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 60),
                  child: TextButton(
                    onPressed: () {},
                    child: Text('완료', style: TextStyle(color: Colors.orangeAccent, fontSize: 24)),
                  ),
                ),
              ]
          ),
        ),
      ),
      body: Column(
        children: [
          Divider(height:2, thickness: 2),
          Container(
              height: 100,
              color: Colors.grey[100],
              child: Padding(
                padding: const EdgeInsets.all(12.0),
                child: Row(
                  children: [
                    Container(
                      width: 100,
                      child: Padding(
                        padding: const EdgeInsets.symmetric( horizontal: 8),
                        child: Material(
                          elevation: 10,
                          child: Container(
                            color: Colors.grey[400],
                            width: 80,
                            child: Center(child: Text("image")),
                          ),
                        ),
                      ),
                    ),
                    Container(
                      width: 100,
                      child: Padding(
                        padding: const EdgeInsets.symmetric( horizontal: 8),
                        child: Container(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text("판매중", style: TextStyle(fontSize: 24)),
                              Text("가격", style: TextStyle(fontSize: 22))
                            ],
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              )
          ),
          Divider(height: 1, thickness: 2),
          Expanded(
            child: Container(
              // color:Colors.amber,
              child: ListView.builder(
                padding: const EdgeInsets.all(16.0),
                itemCount: 20,
                itemBuilder: (context, index) {
                  if(index%2 == 0) {
                    return Padding(
                      padding: const EdgeInsets.only(bottom: 16.0),
                      child: Container(
                          width: double.infinity,
                          // height: 100,
                          // color: Colors.blue,
                          child: Row(
                            crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Column(
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: Container(
                                            width: 60,
                                            height: 60,
                                            // color: Colors.pink,
                                            child: Icon(Icons.account_circle_outlined, size: 60)
                                        ),
                                      ),

                                      Container(
                                          width: 50,
                                          height: 20,
                                          // color: Colors.yellow,
                                          child: Text("닉네임", textAlign: TextAlign.center),
                                      )
                                    ]
                                ),
                                Padding(
                                  padding: const EdgeInsets.only(top: 40.0),
                                  child: Container(
                                      // height: 50,
                                      // width: 100,
                                      color: Colors.grey[300],
                                      child: Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: Text("안녕하세요"),
                                      )
                                  ),
                                )
                              ]
                          )
                      ),
                    );
                  } else {
                    return Padding(
                      padding: const EdgeInsets.only(bottom: 16.0),
                      child: Container(
                          width: double.infinity,
                          // height: 100,
                          // color: Colors.blue,
                          child: Row(
                              mainAxisAlignment: MainAxisAlignment.end,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Padding(
                                  padding: const EdgeInsets.only(top: 40.0),
                                  child: Container(
                                    // height: 50,
                                    // width: 100,
                                      color: Colors.grey[300],
                                      child: Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: Text("안녕하세요"),
                                      )
                                  ),
                                ),
                                Column(
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: Container(
                                            width: 60,
                                            height: 60,
                                            // color: Colors.pink,
                                            child: Icon(Icons.account_circle_outlined, size: 60)
                                        ),
                                      ),

                                      Container(
                                        width: 50,
                                        height: 20,
                                        // color: Colors.yellow,
                                        child: Text("닉네임", textAlign: TextAlign.center),
                                      )
                                    ]
                                ),
                              ]
                          )
                      ),
                    );
                  }
                }
              )
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 16),
            child: Container(
              width: double.infinity,
              // color:Colors.amber,
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    // color: Colors.blue,
                    child:TextButton(
                      child: Container(
                        width: 50,
                        height: 50,
                        child: Icon(Icons.add, size: 50, color: Colors.grey[700]),
                      ),
                      onPressed: () async {
                        Navigator.pop(context);
                      },
                    ),
                  ),
                  Container(
                    width: 240,
                    height: 50,
                    child: Center(
                      child: TextField(
                        decoration: InputDecoration(
                          border: InputBorder.none,
                          hintText: "         메세지 보내기",
                          hintStyle: TextStyle(fontSize: 24)
                        )
                      ),
                    ),
                  ),
                  Container(
                    // color: Colors.blue,
                    child:TextButton(
                      child: Container(
                        width: 50,
                        height: 50,
                        child: Icon(Icons.send, size: 50, color: Colors.grey[700]),
                      ),
                      onPressed: (){},
                    ),
                  ),
                ]
              )
            ),
          )
        ],
      ),
    );
  }
}
