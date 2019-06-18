import 'package:dynamic_theme/dynamic_theme.dart';
import 'package:flutter/material.dart';

class ThemePreferences extends StatelessWidget {
  void changeTheme(bool value, BuildContext context) {
    DynamicTheme.of(context).setBrightness(
      Theme.of(context).brightness == Brightness.dark
          ? Brightness.light
          : Brightness.dark,
    );
  }

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
