import 'package:flutter/cupertino.dart' show CupertinoIcons;
import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/cache/repository/visits_repo.dart';
import 'package:memeory/components/containers/future_builder.dart';
import 'package:memeory/pages/memes/memes.dart';
import 'package:memeory/pages/preferences/preferences.dart';
import 'package:memeory/pages/preferences/profile.dart';
import 'package:memeory/strings/ru.dart';
import 'package:multi_navigator_bottom_bar/multi_navigator_bottom_bar.dart';

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return FutureWidget(
      future: isFirstAppVisit(),
      render: (isFirstVisit) => isFirstVisit
          ? UserPreferencesPage()
          : MultiNavigatorBottomBar(
              initTabIndex: 0,
              tabs: [
                BottomBarTab(
                  initPageBuilder: (_) => FutureWidget(
                    future: getPreferredOrientation(),
                    render: (orientation) => MemesPage(
                      orientation: orientation,
                    ),
                  ),
                  tabIconBuilder: (_) => Icon(CupertinoIcons.home),
                  tabTitleBuilder: (_) => Text(MEMES),
                ),
                BottomBarTab(
                  initPageBuilder: (_) => ProfilePage(),
                  tabIconBuilder: (_) => Icon(CupertinoIcons.profile_circled),
                  tabTitleBuilder: (_) => Text(PROFILE),
                )
              ],
            ),
    );
  }
}
