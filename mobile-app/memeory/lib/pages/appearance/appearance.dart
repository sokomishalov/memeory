import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:memeory/cache/repository/scrolling_axis_repo.dart';
import 'package:memeory/model/scrolling_axis.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/theme/theme.dart';

class AppearancePreferences extends StatefulWidget {
  @override
  _AppearancePreferencesState createState() => _AppearancePreferencesState();
}

class _AppearancePreferencesState extends State<AppearancePreferences> {
  ScrollingAxis _scrollingAxis;

  @override
  void initState() {
    _scrollingAxis = ScrollingAxis.VERTICAL;
    super.initState();
    getPreferredScrollingAxis().then((it) {
      setState(() {
        _scrollingAxis = it;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        SwitchListTile(
          title: Text(t(context, "theme_dark")),
          value: Theme.of(context).brightness == Brightness.dark,
          onChanged: (value) async {
            await changeTheme(context);
          },
        ),
        Container(
          padding: EdgeInsets.symmetric(horizontal: 15),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              Text(
                "Scrolling axis",
                style: TextStyle(fontSize: 16),
              ),
              ToggleButtons(
                selectedColor: Theme.of(context).accentColor,
                fillColor: Theme.of(context).accentColor.withOpacity(0.3),
                children: <Widget>[
                  Icon(FontAwesomeIcons.arrowsAltV),
                  Icon(FontAwesomeIcons.arrowsAltH),
                ],
                onPressed: (int index) async {
                  final _newScrollingAxis = index == 0
                      ? ScrollingAxis.VERTICAL
                      : ScrollingAxis.HORIZONTAL;

                  setState(() {
                    _scrollingAxis = _newScrollingAxis;
                  });

                  await setPreferredScrollingAxis(_newScrollingAxis);
                },
                isSelected: <bool>[
                  _scrollingAxis == ScrollingAxis.VERTICAL,
                  _scrollingAxis == ScrollingAxis.HORIZONTAL,
                ],
              ),
            ],
          ),
        )
      ],
    );
  }
}
