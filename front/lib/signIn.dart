import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:karrot/devTown.dart';
import 'package:karrot/storage.dart';
import 'package:karrot/main.dart';

class LoginForm {
  String email;
  String password;

  LoginForm(this.email, this.password);

  Map<String, dynamic> toJson() => {
    'email' : email,
    'password' : password
  };
}

class SignInPage extends StatefulWidget {

  const SignInPage({
    Key? key,
  }) : super(key: key);

  @override
  State<SignInPage> createState() => _SignInPageState();
}

class _SignInPageState extends State<SignInPage> {

  final controllers = {'email' : TextEditingController(),
  'password': TextEditingController()};

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            children: [
              Padding(padding: EdgeInsets.all(24)),
              Padding(
                child: SizedBox(width: double.infinity, child: Text("로그인", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold), textAlign: TextAlign.left,)),
                padding: EdgeInsets.symmetric(horizontal: 16, vertical: 24),
              ),
              Padding(
                  child: inputBox(controllers['email']!, "이메일(ID)"),
                  padding: EdgeInsets.only(top: 112)
              ),
              Padding(
                  child: inputBox(controllers['password']!, "비밀번호(PW)"),
                  padding: EdgeInsets.only(top: 10)
              ),
              Padding(
                  child: SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        primary: Colors.orangeAccent,
                      ),
                      onPressed: () {_reqLogin(LoginForm(controllers['email']!.text, controllers['password']!.text));},
                      child: Text('로그인', style: TextStyle(color: Colors.white, fontSize: 36)),
                    ),
                  ),
                  padding: EdgeInsets.only(top: 240, left: 10, right: 10)
              ),
              Padding(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text("비밀번호를 잊어버리셨나요?", style: TextStyle(fontSize: 24)),
                      TextButton(
                        onPressed: () {},
                        child: Text('비밀번호 찾기', style: TextStyle(color: Colors.orangeAccent, fontSize: 24)),
                      ),
                    ],
                  ),
                  padding: EdgeInsets.only(top: 20)
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget inputBox(TextEditingController controller, String name) {
    return Column(
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: SizedBox(
              width: double.infinity,
              child: Text(name, style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold), textAlign: TextAlign.left),
            ),
          ),
          Padding(padding:EdgeInsets.all(10)),
          Center(
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: SizedBox(
                height: 48,
                child: TextField(
                  controller: controller,
                    decoration: InputDecoration(
                      border: OutlineInputBorder(),
                    )
                ),
              ),
            ),
          )
        ]

    );
  }

  void _reqLogin(LoginForm form) async {
    print(form.toJson().entries.toString());

    var url = Uri.http(localhost(), '/api/auth/login');
    var response = await http.post(
      url,
      body: jsonEncode(form.toJson()),
      headers: {
        "X-Requested-With": "JSONLoginHttpRequest",
        "Content-Type": "application/json",
      },
    );
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
    print(decodedResponse);
    // 차후 교체
    if (decodedResponse['message'] != "로그인에 성공하였습니다.") {
      showDialog(context: context, builder: (BuildContext context) {
        return AlertDialog(
          title: Text(
              decodedResponse['message'], style: TextStyle(fontSize: 18)),
        );
      });
    }

    else {
      await storage.write(key: "accessToken", value: decodedResponse['data']['accessToken']);
      await storage.write(key: "refreshToken", value: decodedResponse['data']['refreshToken']);
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => DevTownPage()),
      );
    }
  }
}
