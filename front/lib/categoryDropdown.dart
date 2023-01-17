import 'package:flutter/material.dart';

Widget categoryDropdown (List<String> categories) {
  return ListView.builder(
    padding: const EdgeInsets.all(8),
    itemCount: categories.length,
    itemBuilder: (BuildContext context, int index) {
      return Container(
        height: 50,
        child: TextButton(
          onPressed: (){},
          child: Text(categories[index])
        )
      );
    }
  );
}