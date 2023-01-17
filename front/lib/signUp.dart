import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;

import 'package:karrot/signIn.dart';
import 'package:karrot/main.dart';

class SignUpForm {
  String email;
  String password;
  String name;
  String phoneNumber;
  String nickName;

  SignUpForm(this.email, this.password, this.name, this.phoneNumber, this.nickName);

  Map<String, dynamic> toJson() => {
    'email' : email,
    'password': password,
    'name': name,
    'phone': phoneNumber,
    'nickname' : nickName
  };
}

class SignUpPage extends StatefulWidget {

  const SignUpPage({
    Key? key,
  }) : super(key: key);

  @override
  State<SignUpPage> createState() => _SignUpPageState();
}

class _SignUpPageState extends State<SignUpPage> {
  final controllers = {'email' : TextEditingController(),
    'password': TextEditingController(),
    'name': TextEditingController(),
    'phoneNumber': TextEditingController(),
    'nickName': TextEditingController()};

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            children: [
              Padding(padding: EdgeInsets.all(24)),
              Padding(
                child: SizedBox(width: double.infinity, child: Text("회원가입", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold), textAlign: TextAlign.left,)),
                padding: EdgeInsets.symmetric(horizontal: 16, vertical: 24),
              ),
              Padding(
                  child: inputBoxBuilder(controllers['email']!, "이메일(ID)"),
                  padding: EdgeInsets.only(top: 10)
              ),
              Padding(
                  child: inputBoxBuilder(controllers['password']!,"비밀번호(PW)"),
                  padding: EdgeInsets.only(top: 10)
              ),Padding(
                  child: inputBoxBuilder(controllers['name']!, "이름"),
                  padding: EdgeInsets.only(top: 10)
              ),Padding(
                  child: inputBoxBuilder(controllers['phoneNumber']!, "핸드폰번호"),
                  padding: EdgeInsets.only(top: 10)
              ),Padding(
                  child: inputBoxBuilder(controllers['nickName']!, "닉네임"),
                  padding: EdgeInsets.only(top: 10)
              ),
              Padding(
                  child: SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        primary: Colors.orangeAccent,
                      ),
                      onPressed: () {_reqSignUp(SignUpForm(controllers['email']!.text, controllers['password']!.text, controllers['name']!.text, controllers['phoneNumber']!.text, controllers['nickName']!.text));},
                      child: Text('가입하기', style: TextStyle(color: Colors.white, fontSize: 36)),
                    ),
                  ),
                  padding: EdgeInsets.only(top: 40, left: 10, right: 10)
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget inputBoxBuilder(TextEditingController controller, String name) {
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

  void _reqSignUp(SignUpForm form) async {
    // http 요쳥
    print(form.toJson().entries.toString());
    var url = Uri.http(localhost(), '/api/auth/register');
    var response = await http.post(
      url,
      body: jsonEncode(form.toJson()),
      headers: {
        "Content-Type": "application/json",
      },
    );
    var decodedResponse = jsonDecode(utf8.decode(response.bodyBytes)) as Map;
    print(decodedResponse);

    if (decodedResponse['message'] != "회원가입에 성공하였습니다.") {
      showDialog(context: context, builder: (BuildContext context) {
        return AlertDialog(
          title: Text(
              decodedResponse['message'], style: TextStyle(fontSize: 18)),
        );
      });
    }
    else {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => SignInPage()),
      );
    }
  }
}
