import 'package:flutter/material.dart';
import 'package:memeory/api/topics.dart';
import 'package:memeory/util/theme/theme.dart';

class CustomAppBar extends StatefulWidget {
  @override
  _CustomAppBarState createState() => _CustomAppBarState();
}

class _CustomAppBarState extends State<CustomAppBar> {
  String _activeTopic;
  List<dynamic> _topics;

  @override
  void initState() {
    _activeTopic = "all";
    _topics = [];

    super.initState();

    fetchTopics().then((List<dynamic> topics) {
      setState(() {
        _topics = topics;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: Theme(
        child: DropdownButtonHideUnderline(
          child: DropdownButton<String>(
            value: _activeTopic,
            items: <DropdownMenuItem<String>>[
              DropdownMenuItem(
                value: "all",
                child: Text('All memes'),
              ),
              ...(_topics.map(
                (it) => DropdownMenuItem(
                  value: it["id"],
                  child: Text(it["caption"]),
                ),
              ))
            ],
            onChanged: (String value) {
              // todo
            },
          ),
        ),
        data: dependingOnThemeChoice(
          context: context,
          light: new ThemeData.light(),
          dark: new ThemeData.dark(),
        ),
      ),
    );
  }
}
