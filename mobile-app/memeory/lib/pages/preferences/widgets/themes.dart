import 'package:flutter/material.dart';
import 'package:memeory/strings/ru.dart';
import 'package:memeory/theme/theme.dart';

class ThemePreferences extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(50.0),
      child: SwitchListTile(
        title: Text(DARK_THEME),
        value: Theme.of(context).brightness == Brightness.dark,
        onChanged: (value) async {
          await changeTheme(context);
        },
      ),
    );
  }
}
