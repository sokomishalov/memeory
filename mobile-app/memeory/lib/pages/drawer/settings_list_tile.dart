import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/pages/memes/memes.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/pages/preferences/widgets/wrapper.dart';
import 'package:memeory/util/i18n/i18n.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:page_transition/page_transition.dart';

class SettingsListTile extends StatelessWidget {
  const SettingsListTile({
    Key key,
    @required this.icon,
    @required this.title,
    @required this.pageBuilder,
  }) : super(key: key);

  final IconData icon;
  final String title;
  final WidgetBuilder pageBuilder;

  @override
  Widget build(BuildContext context) {
    return ListTile(
      leading: Icon(icon),
      title: Text(title),
      onTap: () {
        pushToPrefs(
          title: title,
          context: context,
          body: pageBuilder(context),
        );
      },
    );
  }

  void pushToPrefs({
    @required BuildContext context,
    String title,
    Widget body,
    Future apply,
  }) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (pageContext) => Scaffold(
          body: PreferencesPageWrapper(
            title: title,
            apply: () async {
              if (apply != null) await apply;
              var scrollingAxis = await getPreferredScrollingAxis();

              Navigator.pushReplacementNamed(
                context,
                ROUTES.MEMES.route,
                arguments: MemesScreenArgs(scrollingAxis: scrollingAxis),
                result: PageTransition(
                  type: PageTransitionType.leftToRightWithFade,
                  child: MemesPage(),
                ),
              );
            },
            applyText: t(context, "back_to_watch_memes"),
            child: body,
          ),
        ),
      ),
    );
  }
}
