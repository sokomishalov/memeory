import 'package:flutter/material.dart';
import 'package:memeory/util/theme.dart';

class ThemePreferences extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(50.0),
      child: SwitchListTile(
        title: Text("Темная тема"),
        value: Theme.of(context).brightness == Brightness.dark,
        onChanged: (value) => changeTheme(value, context),
      ),
    );
  }
}
