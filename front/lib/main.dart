/*
  남은 일 :
  signUp : 없음 (완료)
  signIn : 허가받지 않은 유저메시지 해결, 랜딩페이지 변경(가능하면)
  search : Api콜을 통해 itemList 받아오기, item 눌러 detail 이행
  category : 없음 (완료)
  devTown :
  detail :
  selling
  write
  chat
  myKarrot : 없음 (완료)
  profile
  favorite
  chatList
  userSellList
  userSellDetail
  sellChatList
  main : 없음 (완료)

 */

import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

import 'package:karrot/signUp.dart';
import 'package:karrot/signIn.dart';

String path(str) {
  return (kIsWeb) ? str : str;
}

String localhost() {
  return (kIsWeb) ? 'localhost:8080' : '10.0.2.2:8080';
}

void main() => runApp(Main());

class Main extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.orange,
      ),
      home: MainPage(),
      // home: SearchPage(),
      // home: SignInPage(),
      // home: SignUpPage(),
      // home: CategoryPage(),
      // home: DevTownPage(),
      // home: DetailPage(id: 1),
      // home: SellingPage(),
      // home: WritePage(),
      // home: ChatPage(),
      // home: MyKarrotPage(),
      // home: ProfilePage(),
      // home: FavoritePage(),
      // home: ChatListPage(),
      // home: UserSellListPage(),
      // home: UserSellDetailPage(),
      // home: SellChatListPage(),
    );
  }
}

class MainPage extends StatefulWidget {

  const MainPage({
    Key? key,
  }) : super(key: key);

  @override
  State<MainPage> createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  int _counter = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // carrot image
            Padding(
                child: Container(
                  height: 250,
                  width: 250,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: AssetImage(
                          path('assets/free-icon-carrot-7654244.png')),
                      fit: BoxFit.fill,
                    ),
                  ),
                ),
                padding: EdgeInsets.only(top: 48, bottom: 24)
            ),
            Padding(
                child: Text("당신 근처의 Karrot 마켓", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 36)),
                padding: EdgeInsets.only(top: 10)
            ),
            Padding(
                child: Text("이제 내 동네에서\n간편하게 중고거래하자", style: TextStyle(fontSize: 24), textAlign: TextAlign.center),
                padding: EdgeInsets.only(top: 20)
            ),

            Padding(
                child: SizedBox(
                  width: double.infinity,
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      primary: Colors.orangeAccent,
                    ),
                      onPressed: () {Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => SignUpPage()),
                      );},
                    child: Text('시작하기', style: TextStyle(color: Colors.white, fontSize: 36)),
              ),
                ),
              padding: EdgeInsets.only(top: 150, left: 20, right: 20, bottom: 0)
            ),
            Padding(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text("이미 계정이 있나요?", style: TextStyle(fontSize: 20)),
                    TextButton(
                      onPressed: () {Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => SignInPage()),
                      );},
                      child: Text('로그인', style: TextStyle(color: Colors.orangeAccent, fontSize: 20)),
                    ),
                  ],
                ),
                padding: EdgeInsets.only(top: 30)
            )
          ],
        ),
      ),
    );
  }
}
