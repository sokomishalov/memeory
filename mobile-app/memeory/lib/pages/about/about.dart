import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:memeory/components/buttons/rounded_button.dart';
import 'package:memeory/components/containers/web_view.dart';
import 'package:memeory/components/text/link_text_span.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/theme/dark.dart';
import 'package:memeory/util/theme/light.dart';
import 'package:memeory/util/theme/theme.dart';

class AboutApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: <Widget>[
          Text("Memeory"),
          Padding(
            padding: EdgeInsets.only(top: 24.0),
            child: RichText(
              text: TextSpan(
                children: <TextSpan>[
                  TextSpan(
                      text: t(context, "made_by"),
                      style: TextStyle(
                        color: dependingOnThemeChoice(
                          context: context,
                          light: TEXT_COLOR_LIGHT,
                          dark: TEXT_COLOR_DARK,
                        ),
                      )),
                  LinkTextSpan(
                    context: context,
                    url: CREATOR_LINK,
                    text: "@${t(context, "creator")}",
                  )
                ],
              ),
            ),
          ),
          Container(
            padding: EdgeInsets.only(top: 10),
            child: RoundedButton(
              caption: t(context, "donate"),
              onPressed: () {
                openMemeoryWebView(context, YANDEX_DONATE_PAGE);
              },
            ),
          )
        ],
      ),
    );
  }
}
