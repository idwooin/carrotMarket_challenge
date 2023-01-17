import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;

String path(str) {
  return (kIsWeb) ? 'assets/$str' : str;
}

class Item {
  String imgPath;
  String title;
  String location;
  int price;
  int favorite;
  Item(this.imgPath, this.title, this.location, this.price, this.favorite);
}

class SearchPage extends StatefulWidget {

  const SearchPage({
    Key? key,
  }) : super(key: key);

  @override
  State<SearchPage> createState() => _SearchPageState();
}

class _SearchPageState extends State<SearchPage> {

  final searchController = TextEditingController();
  List<Item> itemList = <Item>[];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        toolbarHeight: 80,
        elevation: 0,
        backgroundColor: Colors.white,
        title:Padding(
          padding: const EdgeInsets.all(12),
          child: Row(
              children: [
                Padding(
                    padding: EdgeInsets.only(right: 24),
                    child: IconButton(
                      icon: Icon(Icons.arrow_back, size: 32),
                      onPressed: (){Navigator.pop(context);},
                    )
                ),
                Expanded(
                  child: SizedBox(
                    height: 48,
                    child: Padding(
                      padding: EdgeInsets.only(),
                      child: TextField(
                        controller: searchController,
                          decoration: InputDecoration(
                              filled: true,
                              fillColor: Colors.grey[280],
                              focusColor: Colors.grey[280],
                              hintText: "우리동네에서 검색",
                              border: OutlineInputBorder()
                          ),
                      ),
                    ),
                  ),
                ),
                Padding(
                    padding: EdgeInsets.only(left: 8),
                    child: IconButton(
                      icon: Icon(Icons.search, size: 32),
                      onPressed: () async {
                        itemList = await _loadItemList(searchController.text);
                        setState(() {

                        });
                      },
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
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(0),
                child: ListView.separated(
                  padding: const EdgeInsets.all(0),
                  itemCount: itemList.length,
                  itemBuilder: (BuildContext context, index) {
                    return Padding(
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
                                        decoration: BoxDecoration(
                                          image: DecorationImage(
                                            image: AssetImage(
                                                path(itemList[index].imgPath)),
                                            fit: BoxFit.fill,
                                          ),
                                        ),
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
                                            Text(itemList[index].title, style: TextStyle(fontSize: 20)),
                                          ],
                                        ),
                                        Padding(padding: EdgeInsets.only(top: 4)),
                                        Text(itemList[index].location, style: TextStyle(fontSize: 18, color: Colors.grey)),
                                        Padding(padding: EdgeInsets.only(top: 4)),
                                        Text(itemList[index].price.toString(), style: TextStyle(fontSize: 18)),
                                      ]
                                  ),
                                ]
                              ),
                              if(itemList[index].favorite != 0) Row(
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
                                                    icon: Icon(Icons.favorite_border, size: 30),
                                                    onPressed: (){},
                                                  ),
                                                ),
                                                Padding(
                                                  padding: const EdgeInsets.only(top: 10),
                                                  child: Center(child: Text(itemList[index].favorite.toString(), style: TextStyle(fontSize: 16))),
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
                    );
                  },
                  separatorBuilder: (BuildContext context, int index) => const Divider(height: 1, thickness: 2),
                ),
              ),
            )
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    // Clean up the controller when the widget is removed from the
    // widget tree.
    searchController.dispose();
    super.dispose();
  }

  Future<List<Item>> _loadItemList(search) async {
    List<Item> itemList = <Item>[];
    for(var i = 0;i< 20;i++)
      itemList.add(Item(path("assets/free-icon-carrot-7654244.png"), "급처", "시립대", 12000, 4));
    return itemList;
  }

}
