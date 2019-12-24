import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/scrolling_axis_repo.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:memeory/util/theme/theme.dart';

class MemeoryDrawerHeader extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 110,
      child: DrawerHeader(
        child: Stack(
          children: <Widget>[
            GestureDetector(
              onTap: () => Navigator.pop(context),
              child: Icon(Icons.close),
            ),
            GestureDetector(
              onTap: () async {
                Navigator.pushReplacementNamed(
                  context,
                  ROUTES.MEMES.route,
                  arguments: MemesScreenArgs(
                    scrollingAxis: await getPreferredScrollingAxis(),
                  ),
                );
              },
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  Container(
                    width: 30,
                    height: 30,
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      image: DecorationImage(
                        image: dependingOnThemeChoice(
                          context: context,
                          light: AssetImage(LOGO_ASSET),
                          dark: AssetImage(LOGO_INVERTED_ASSET),
                        ),
                      ),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.only(left: 10),
                    child: Text(
                      'Memeory!',
                      style: TextStyle(
                        fontSize: 15,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
