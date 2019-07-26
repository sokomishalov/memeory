import 'package:dynamic_theme/dynamic_theme.dart';
import 'package:flutter/material.dart';

ThemeData themeBuilder(Brightness brightness) {
  return brightness == Brightness.light
      ? ThemeData.light().copyWith(
          textTheme: ThemeData.light()
              .textTheme
              .apply(
                fontFamily: 'Memeory',
              )
              .copyWith(
                subtitle: TextStyle(
                  color: Colors.black.withOpacity(0.4),
                ),
              ),
          primaryColor: Colors.greenAccent,
          buttonTheme: ButtonThemeData(
            buttonColor: Colors.greenAccent,
            textTheme: ButtonTextTheme.primary,
          ),
        )
      : ThemeData.dark().copyWith(
          textTheme: ThemeData.dark()
              .textTheme
              .apply(
                fontFamily: 'Memeory',
              )
              .copyWith(
                subtitle: TextStyle(
                  color: Colors.white.withOpacity(0.6),
                ),
              ),
          errorColor: Colors.redAccent[200],
          accentColor: Colors.tealAccent,
          buttonTheme: ButtonThemeData(
            buttonColor: Colors.teal,
          ),
          primaryColorLight: Color.fromRGBO(26, 26, 27, 1),
          primaryColor: Color.fromRGBO(26, 26, 27, 1),
          primaryColorDark: Colors.black,
          toggleableActiveColor: Colors.teal[300],
        );
}

IconThemeData getDefaultIconThemeData(context) {
  return Theme.of(context).brightness == Brightness.light
      ? ThemeData.light().iconTheme
      : ThemeData.dark().iconTheme;
}

TextTheme getDefaultTextTheme(context) {
  return Theme.of(context).brightness == Brightness.light
      ? ThemeData.light().textTheme
      : ThemeData.dark().textTheme;
}

dynamic dependingOnThemeChoice({context, light, dark}) {
  return Theme.of(context).brightness == Brightness.light ? light : dark;
}

changeTheme(BuildContext context) async {
  bool wantToUseDarkThemeNow = Theme.of(context).brightness == Brightness.light;

  DynamicTheme.of(context).setBrightness(
    wantToUseDarkThemeNow ? Brightness.dark : Brightness.light,
  );
}
