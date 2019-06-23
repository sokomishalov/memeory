import 'package:flutter/material.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/pages/memes/memes_horizontal.dart';
import 'package:memeory/pages/memes/memes_vertical.dart';

class MemesPage extends StatelessWidget {
  const MemesPage({@required this.orientation});

  final MemesOrientation orientation;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
        preferredSize: Size.fromHeight(40),
        child: AppBar(
          centerTitle: true,
          title: Text("Мемесы"),
        ),
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            DrawerHeader(
              child: Text('Мемеори!'),
              decoration: BoxDecoration(
                color: Theme.of(context).primaryColorLight,
              ),
            ),
            ListTile(
              title: Text('Профиль'),
              onTap: () => Navigator.pop(context),
            ),
            ListTile(
              title: Text('Источники'),
              onTap: () => Navigator.pop(context),
            ),
            ListTile(
              title: Text('О приложении'),
              onTap: () => Navigator.pop(context),
            )
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
