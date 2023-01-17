import 'dart:typed_data';


import 'package:dio/dio.dart' as dio;
import 'package:flutter/material.dart';
import 'package:async/async.dart';

import 'package:http_parser/http_parser.dart';

import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:file_picker/file_picker.dart';


import 'package:karrot/signIn.dart';
import 'package:karrot/storage.dart';
import 'package:karrot/main.dart';

class WriteForm {
  String title;
  String stockCategory;
  String price;
  String contents;

  WriteForm(this.title, this.stockCategory, this.price, this.contents);

  Map<String, dynamic> toJson() =>{
      'title' : title,
      'stockCategory' : stockCategory,
      'price' : price,
      'contents' : contents
};
}

class WritePage extends StatefulWidget {

  final String id;

  const WritePage({
    Key? key, required this.id,
  }) : super(key: key);

  @override
  State<WritePage> createState() => _WritePageState();
}

class _WritePageState extends State<WritePage> {
  final controllers = {'title': TextEditingController(),
    'price': TextEditingController(),
    'contents': TextEditingController(),
  };

  final categories = ['디지털기기', '생활가전', '가구/인테리어', '유아동', '생활/가공식품', '유아도서', '스포츠/레저', '여성잡화', '여성의류', '남성패션/잡화', '게임/취미', '뷰티/미용', '반려동물용품', '도서/티켓/음반', '식물', '기타 중고물품', '중고차'];
  final categorymap = {
  "디지털기기": "DIGITAL",
    "생활가전": "HOUSEHOLD_APPLIANCES",
    "가구/인테리어": "FURNITURE",
    "유아동" : "CHILD",
    "생활/가공식품": "FOOD",
    "유아도서": "CHILD_BOOK",
    "스포츠/레저": "SPORTS",
    "여성잡화": "FEMALE_STUFF",
    "여성의류": "FEMALE_CLOTHES",
    "남성패션/잡화": "MALE_CLOTHES",
    "게임/취미": "GAME",
    "뷰티/미용": "BEAUTY",
    "반려동물용품":"PET",
    "도서/티켓/음반": "BOOK",
    "식물": "PLANTS",
    "기타 중고물품":"USED",
    "중고차": "USED_CAR"
  };
  late final reverseCategoryMap;

  int current = -1;

  List<Uint8List> images = <Uint8List>[];

  @override
  void initState() {
    reverseCategoryMap = Map.fromIterables(categorymap.values, categorymap.keys);
    if(widget.id != "") {
      _loadDetail(widget.id).then((result) async {
        controllers['title']!.text = result['title'];
        controllers['price']!.text = result['price'];
        controllers['contents']!.text = result['contents'];

        for(int i=0;i<result['img'].length;i++){
          var a = await http.get(Uri.parse(result['img'][i]));
          images.add(a.bodyBytes);
        }

        current = categories.indexOf(reverseCategoryMap[result['stockCategory']]);
        setState(() {});
      });
    }
    // TODO: implement initState
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
                  padding: EdgeInsets.only(right: 4),
                  child: IconButton(
                    icon: Icon(Icons.arrow_back, size: 32),
                    onPressed: (){
                      Navigator.pop(context);
                    },
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
                    onPressed: () async {
                      await _reqWrite(WriteForm(controllers['title']!.text, categorymap[categories[current]]!, controllers['price']!.text, controllers['contents']!.text));
                      Navigator.pop(context);
                    },
                    child: Text('완료', style: TextStyle(color: Colors.orangeAccent, fontSize: 24)),
                  ),
              ),
            ]
        ),
      ),
    ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Divider(height:2, thickness: 2),
            Container(
                height: 150,
                color: Colors.grey[100],
                child: Padding(
                  padding: const EdgeInsets.all(12.0),
                  child: ListView.builder(
                    scrollDirection: Axis.horizontal,
                    itemCount: images.length + 1,
                    itemBuilder: (context, index) {
                      if (index == 0) { return Container(
                        width: 100,
                        child: Padding(
                          padding: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 8),
                          child: Material(
                            elevation: 10,
                            child: Ink(
                              color: Colors.white,
                              width: 80,
                              child: InkWell(
                                onTap:() async {
                                  FilePickerResult? result = await FilePicker.platform.pickFiles();

                                  if (result != null) {
                                    images.add(result.files.single.bytes!);
                                    setState(() {

                                    });
                                  } else {
                                    // User canceled the picker
                                  }
                                },
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: [
                                    Icon(Icons.camera_alt_outlined, size: 48),
                                    Text("${images.length}/10")
                                  ],
                                ),
                              ),
                            ),
                          ),
                        ),
                      );} else { return Container(
                      width: 100,
                      child: Stack(
                        children: [
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 8),
                          child: Material(
                            elevation: 10,
                            child: Container(
                              child: Image.memory(Uint8List.fromList(images[index-1])),
                              width: 80,
                              height: 100,

                            ),
                          ),
                        ),
                          Padding(
                            padding: EdgeInsets.only(left: 70),
                            child: Container(
                              width: 36,
                              height: 36,
                              child: FloatingActionButton(
                                backgroundColor: Colors.transparent,
                                elevation: 0,
                                child: Icon(Icons.remove_circle_outline, size: 36),
                                onPressed: (){
                                  images.removeAt(index-1);
                                  setState(() {

                                  });
                                }
                              ),
                            ),
                          ),
                        ]
                      ),
                    );}},
                  ),
                )
            ),
            Divider(height: 1, thickness: 2),
            Container(
                height: 100,
                width: double.infinity,
                color: Colors.grey[100],
                child: Center(
                  child: TextField(
                    controller: controllers['title'],
                    style: TextStyle(color: Colors.black, fontSize: 24),
                      decoration: InputDecoration(
                        border: InputBorder.none,
                        hintText: " 제목",
                        hintStyle: TextStyle(color: Colors.black, fontSize: 24),
                      )
                  ),
                )
            ),
            Divider(height: 1, thickness: 2),
            Container(
                height: 100,
                color: Colors.grey[100],
                child: TextButton(
                  child: Container(
                      width: double.infinity,
                      child: Text((current==-1) ? "카테고리" : categories[current], textAlign: TextAlign.left, style: TextStyle(fontSize: 24, color: Colors.black))),
                  onPressed: () async {
                    current = await showDialog(
                    context: context,
                    builder: (context){return categoryDropdown(categories);},
                    );
                    setState(() {
                    });
                  },
                )
            ),
            Divider(height: 1, thickness: 2),
            Container(
                height: 100,
                width: double.infinity,
                color: Colors.grey[100],
                child: Center(
                  child: TextField(
                    controller: controllers['price'],
                      style: TextStyle(color: Colors.black, fontSize: 24),
                      decoration: InputDecoration(
                        border: InputBorder.none,
                        hintText: " 가격(원)",
                        hintStyle: TextStyle(color: Colors.black, fontSize: 24),
                      )
                  ),
                )
            ),
            Divider(height: 1, thickness: 2),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(

                  color: Colors.grey[100],
                  child: TextField(
                    controller: controllers['contents'],
                    decoration: InputDecoration(
                        border: InputBorder.none,
                        hintMaxLines: 2,
                        hintText:
                    "게시글 내용을 작성해주세요. 가짜 품목 및 판매 금지 품목\n은 게시가 제한됩니다."),
                  )
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget categoryDropdown (List<String> categories) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 36),
      child: Container(
        color: Colors.white,
        child: ListView.builder(
            padding: const EdgeInsets.all(24),
            itemCount: categories.length,
            itemBuilder: (BuildContext context, int index) {
              return TextButton(
                onPressed: (){
                  Navigator.pop(context, index);
                },
                child: Container(
                  width: double.infinity,
                  height: 50,
                  child: Text(categories[index], textAlign: TextAlign.left, style: TextStyle(fontSize: 24, color: Colors.black))
                )
              );
            }
        ),
      ),
    );
  }

  Future<dynamic> _loadDetail(id) async {
    final String? token = await storage.read(key: "accessToken");


    var url = Uri.http(localhost(), '/api/posts/${id}');
    var response = await http.get(
      url,
      headers: {
        'Authorization': 'Bearer $token'
      },
    );
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;

    print(decodedResponse);
    return decodedResponse['data'];
  }

  Future<dynamic> _reqWrite(WriteForm form) async {

    final String? token = await storage.read(key: "accessToken");
    print(token);
    var url;
    if(widget.id == "") {
      url = Uri.http(localhost(), '/api/posts');
    } else {
      url = Uri.http(localhost(), '/api/posts/${widget.id}');
    }

    var diodio = dio.Dio();

    diodio.options = dio.BaseOptions(
      headers: {
        "authorization" : 'Bearer ${token}'

      }
    );

    diodio.options.contentType = 'multipart/form-data';

    List<dio.MultipartFile> list = <dio.MultipartFile>[];
    for(int i =0;i<images.length;i++) {
      var multipartFile = dio.MultipartFile.fromBytes(
          images[i], filename: 'image$i.jpeg', contentType: MediaType('image','jpeg'));

      list.add(multipartFile);
    }



    var json =  dio.MultipartFile.fromString(jsonEncode(form.toJson()), contentType: MediaType('application','json'));


    var formData;
    var response;


    if(widget.id == "") {
      // request = http.MultipartRequest("POST", url);
      // MultipartFile
      // request.headers['authorization'] = 'Bearer $token';
      //
      // request.files.add(json);
      //
      // request.files.addAll(list);

      formData = dio.FormData.fromMap({
        "postCreateRequest" : json,
        "multipartFiles" : list
      });

      response = await diodio.postUri(url,data: formData);

    } else {
      // request = http.MultipartRequest("PUT", url);
      // request.headers['authorization'] = 'Bearer $token';
      //
      // request.files.addA(http.MultipartFile.fromString("postCreateRequest", jsonEncode(form.toJson()), contentType: MediaType('application','json')));
      //
      // response = await request.send();
      formData = dio.FormData.fromMap({
        "postCreateRequest" : json,
      });

      response = await diodio.putUri(url,data: formData);

    }
  }
}
