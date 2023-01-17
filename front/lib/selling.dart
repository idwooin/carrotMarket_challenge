import 'package:flutter/material.dart';

class SellingPage extends StatefulWidget {

  const SellingPage({
    Key? key,
  }) : super(key: key);

  @override
  State<SellingPage> createState() => _SellingPageState();
}

class _SellingPageState extends State<SellingPage> {

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: AppBar(
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
                        onPressed: (){},
                      )
                  ),
                  Padding(
                    padding: EdgeInsets.only(right: 24),
                    child: Text(
                      "판매상품 보기",
                      style: TextStyle(fontSize: 32),
                    ),
                  ),
                ]
            ),
          ),
          bottom: PreferredSize(
            preferredSize: Size(double.infinity, 70),
            child: TabBar(tabs: [
              Container(
                height: 50,
                child: Tab(text: "전체"),
              ),
              Container(
                height: 50,
                child: Tab(text: "판매중"),
              ),
              Container(
                height: 50,
                child: Tab(text: "거래완료"),
              ),
            ],
              indicator: UnderlineTabIndicator(
              borderSide: BorderSide(width: 3.0, color: Colors.grey),
              ),
              labelStyle: TextStyle(fontSize: 24),
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
                              height: 120,
                              child: Row(
                                  children: [
                                    Container(
                                      width: 120,
                                      height: 120,
                                      color: Colors.amber,
                                    ),
                                    Padding(padding: EdgeInsets.symmetric(horizontal: 16)),
                                    Column(
                                        children: [
                                          Text("제목", style: TextStyle(fontSize: 20)),
                                          Text("장소", style: TextStyle(fontSize: 18, color: Colors.grey)),
                                          Text("가격", style: TextStyle(fontSize: 18)),
                                        ]
                                    ),
                                    Padding(padding: EdgeInsets.symmetric(horizontal: 50)),
                                    Column(
                                      children: [
                                        Padding(padding: EdgeInsets.symmetric(vertical: 25)),
                                        Container(
                                            width: 60,
                                            height: 30,
                                            decoration: BoxDecoration(
                                              color: Colors.black,
                                              borderRadius: BorderRadius.circular(3 )
                                            ),
                                            child: Center(child: Text("거래완료", style: TextStyle(color: Colors.white))),
                                        ),
                                        Container(
                                            width: 90,
                                            height: 30,
                                            child: Row(
                                                children: [
                                                  Padding(
                                                    padding: const EdgeInsets.all(0),
                                                    child: IconButton(
                                                      icon: Icon(Icons.favorite_border, size: 30),
                                                      onPressed: (){},
                                                    ),
                                                  ),
                                                  Padding(
                                                    padding: const EdgeInsets.only(top: 10),
                                                    child: Center(child: Text("n", style: TextStyle(fontSize: 16))),
                                                  )
                                                ]
                                            )
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
