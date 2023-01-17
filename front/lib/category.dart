import 'package:flutter/material.dart';

class CategoryPage extends StatefulWidget {

  const CategoryPage({
    Key? key,
  }) : super(key: key);

  @override
  State<CategoryPage> createState() => _CategoryPageState();
}

class _CategoryPageState extends State<CategoryPage> {
  final categories = ['디지털기기', '생활가전', '가구/인테리어', '유아동', '생활/가공식품', '유아도서', '스포츠/레저', '여성잡화', '여성의류', '남성패션/잡화', '게임/취미', '뷰티/미용', '반려동물용품', '도서/티켓/음반', '식물', '기타 중고물품', '중고차'];

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
                    child: IconButton(
                      icon: Icon(Icons.arrow_back, size: 32),
                      onPressed: (){Navigator.pop(context, -1);},
                    )
                ),
                Expanded(
                  child: SizedBox(
                    height: 48,
                    child: Padding(
                        padding: EdgeInsets.only(),
                        child: Text(
                          "카테고리",
                          style: TextStyle(fontSize: 32, fontWeight: FontWeight.bold),
                        )
                    ),
                  ),
                )
              ]
          ),
        ),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            children: [
              Divider(height:2, thickness: 2),
              Padding(
                padding: EdgeInsets.all(24),
                child: Table(
                  // border: TableBorder.symmetric(inside: BorderSide(width: 1)),
                  columnWidths: const <int, TableColumnWidth>{
                    0: FlexColumnWidth(),
                    1: FlexColumnWidth(),
                    2: FlexColumnWidth(),
                  },
                  children: [
                    TableRow(
                        children: <Widget>[
                          itemBoxBuilder(0),
                          itemBoxBuilder(1),
                          itemBoxBuilder(2),
                        ]
                    ),TableRow(
                        children: <Widget>[
                          itemBoxBuilder(3),
                          itemBoxBuilder(4),
                          itemBoxBuilder(5),
                        ]
                    ),TableRow(
                        children: <Widget>[
                          itemBoxBuilder(6),
                          itemBoxBuilder(7),
                          itemBoxBuilder(8),
                        ]
                    ),TableRow(
                        children: <Widget>[
                          itemBoxBuilder(9),
                          itemBoxBuilder(10),
                          itemBoxBuilder(11),
                        ]
                    ),TableRow(
                        children: <Widget>[
                          itemBoxBuilder(12),
                          itemBoxBuilder(13),
                          itemBoxBuilder(14),
                        ]
                    ),
                  ]
                )
              )
            ],
          ),
        ),
      ),
    );
  }
  
  Widget itemBoxBuilder(int item) {
    return SizedBox(
      height: 80,
      child: Center(child: TextButton(
        child: Text(categories[item], textAlign: TextAlign.center, style: TextStyle( color: Colors.black)),
        onPressed: () {Navigator.pop(context, item);},
      )),
    );
  }
}
