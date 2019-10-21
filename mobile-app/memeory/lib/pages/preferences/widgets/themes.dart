import 'package:flutter/material.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/theme/theme.dart';

class ThemePreferences extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.all(50.0),
      child: SwitchListTile(
        title: Text(t(context, "theme_dark")),
        value: Theme.of(context).brightness == Brightness.dark,
        onChanged: (value) async {
          await changeTheme(context);
        },
      ),
    );
  }
}
