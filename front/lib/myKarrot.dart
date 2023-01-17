import 'package:flutter/material.dart';

import 'package:karrot/main.dart';

class MyKarrotPage extends StatefulWidget {

  const MyKarrotPage({
    Key? key,
  }) : super(key: key);

  @override
  State<MyKarrotPage> createState() => _MyKarrotPageState();
}

class _MyKarrotPageState extends State<MyKarrotPage> {

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
                        onPressed: () {
                          }
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
                height: 150,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Icon(Icons.account_circle_outlined, size: 100),
                      Padding(padding: EdgeInsets.only(left: 8)),
                      Text("닉네임", style: TextStyle(fontSize: 36)),
                    ]
                ),
              ),
              TextButton(
                onPressed: () {},
                child: Container(
                  width: double.infinity,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(8),
                    border: Border.all(width: 3)
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(4.0),
                    child: Text('프로필 수정', textAlign: TextAlign.center, style: TextStyle(color: Colors.black, fontSize: 36)),
                  )
                ),
              ),
              TextButton(
                onPressed: () {},
                child: Container(
                    width: double.infinity,
                    child: Padding(
                      padding: const EdgeInsets.all(24.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Icon(Icons.library_books, size: 54),
                          Padding(padding: EdgeInsets.symmetric(horizontal: 48)),
                          Text('판매내역', textAlign: TextAlign.center, style: TextStyle(color: Colors.black, fontSize: 36)),
                        ],
                      ),
                    )
                ),
              ),
              TextButton(
                onPressed: () {},
                child: Container(
                    width: double.infinity,
                    child: Padding(
                      padding: const EdgeInsets.all(24.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Icon(Icons.favorite_border, size: 54),
                          Padding(padding: EdgeInsets.symmetric(horizontal: 48)),
                          Text('관심목록', textAlign: TextAlign.center, style: TextStyle(color: Colors.black, fontSize: 36)),
                        ],
                      ),
                    )
                ),
              ),
              TextButton(
                onPressed: () {},
                child: Container(
                    width: double.infinity,
                    child: Padding(
                      padding: const EdgeInsets.all(24.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Icon(Icons.chat, size: 54),
                          Padding(padding: EdgeInsets.symmetric(horizontal: 48)),
                          Text('채팅목록', textAlign: TextAlign.center, style: TextStyle(color: Colors.black, fontSize: 36)),
                        ],
                      ),
                    )
                ),
              ),
              TextButton(
                onPressed: () {

                },
                child: Container(
                    width: double.infinity,
                    child: Padding(
                      padding: const EdgeInsets.all(0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text('Log out', textAlign: TextAlign.center, style: TextStyle(color: Colors.black, fontSize: 36)),
                        ],
                      ),
                    )
                ),
              ),
            ],
          ),
        ),
    );
  }
}
