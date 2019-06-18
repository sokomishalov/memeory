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
          primaryColorLight: Color(0xFF444444),
          toggleableActiveColor: Colors.teal[300],
        );
}

Color dependingOnThemeChoice({light, dark, context}) {
  return Theme.of(context).brightness == Brightness.light ? light : dark;
}
