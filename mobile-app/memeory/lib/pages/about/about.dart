import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:memeory/common/buttons/rounded_button.dart';
import 'package:memeory/common/components/link_text_span.dart';
import 'package:memeory/common/containers/web_view.dart';
import 'package:memeory/strings/ru.dart';
import 'package:memeory/util/consts.dart';

class AboutApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        children: <Widget>[
          Text(APP_NAME),
          Padding(
            padding: const EdgeInsets.only(top: 24.0),
            child: RichText(
              text: TextSpan(
                children: <TextSpan>[
                  TextSpan(
                    text: MADE_BY,
                  ),
                  LinkTextSpan(
                    context: context,
                    url: CREATOR_LINK,
                    text: "@$CREATOR",
                  )
                ],
              ),
            ),
          ),
          Container(
            padding: EdgeInsets.only(top: 10),
            child: RoundedButton(
              caption: DONATE,
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
