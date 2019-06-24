import 'package:flutter/material.dart';
import 'package:memeory/common/message/messages.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';
import 'package:memeory/util/theme.dart';

class MemesPage extends StatelessWidget {
  const MemesPage({
    this.orientation = MemesOrientation.VERTICAL,
  });

  final MemesOrientation orientation;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(50),
        child: AppBar(
          centerTitle: true,
          title: Text("Мемесы"),
        ),
      ),
      drawer: Drawer(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            DrawerHeader(
              child: Stack(
                children: <Widget>[
                  GestureDetector(
                    onTap: () => Navigator.pop(context),
                    child: Icon(Icons.close),
                  ),
                  Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        Container(
                          width: 70,
                          height: 70,
                          decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            image: DecorationImage(
                              image: dependingOnThemeChoice(
                                context: context,
                                light: AssetImage(
                                  "assets/logo/logo.png",
                                ),
                                dark: AssetImage(
                                  "assets/logo/logo-inverted.png",
                                ),
                              ),
                            ),
                          ),
                        ),
                        Container(
                          padding: EdgeInsets.only(top: 10),
                          child: Text('Мемеори!'),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              decoration: BoxDecoration(
                color: Theme.of(context).primaryColorLight,
              ),
            ),
            Expanded(
              child: ListView(
                padding: EdgeInsets.zero,
                children: <Widget>[
                  ListTile(
                    leading: Icon(Icons.person_outline),
                    title: Text('Профиль'),
                    onTap: () {
                      infoToast("Пока не реализовано", context);
                      Navigator.pop(context);
                    },
                  ),
                  ListTile(
                    leading: Icon(Icons.star_border),
                    title: Text('Источники'),
                    onTap: () {
                      infoToast("Пока не реализовано", context);
                      return Navigator.pop(context);
                    },
                  ),
                  ListTile(
                    leading: Icon(Icons.info_outline),
                    title: Text('О приложении'),
                    onTap: () {
                      infoToast("Пока не реализовано", context);
                      return Navigator.pop(context);
                    },
                  )
                ],
              ),
            ),
            Container(
              height: 50,
              margin: EdgeInsets.only(bottom: 20),
              child: SwitchListTile(
                title: Text("Темная тема"),
                value: Theme.of(context).brightness == Brightness.dark,
                onChanged: (value) => changeTheme(value, context),
              ),
            ),
          ],
        ),
      ),
      body: orientation == MemesOrientation.VERTICAL
          ? MemesVertical()
          : MemesHorizontal(),
      backgroundColor: Theme.of(context).primaryColorDark,
    );
  }
}
