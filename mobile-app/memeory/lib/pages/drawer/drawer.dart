import 'package:flutter/material.dart';
import 'package:memeory/api/providers.dart';
import 'package:memeory/api/topics.dart';
import 'package:memeory/components/images/provider_logo.dart';
import 'package:memeory/pages/drawer/drawer_header.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:memeory/util/strings/strings.dart';

class CustomDrawer extends StatefulWidget {
  @override
  _CustomDrawerState createState() => _CustomDrawerState();
}

class _CustomDrawerState extends State<CustomDrawer> {
  List<dynamic> _topics;
  List<dynamic> _providers;

  @override
  void initState() {
    _topics = [];
    _providers = [];

    super.initState();

    Future.wait([
      fetchTopics(),
      fetchProviders(),
    ]).then((List<List<dynamic>> data) {
      setState(() {
        _topics = data[0];
        _providers = data[1];
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          MemeoryDrawerHeader(),
          Expanded(
            child: ListView(
              itemExtent: 50,
              padding: EdgeInsets.zero,
              children: <Widget>[
                Container(
                  padding: EdgeInsets.only(top: 10, left: 10),
                  child: Text(
                    "Topics",
                    style: TextStyle(fontSize: 18),
                  ),
                ),
                ...(_topics.map((it) => ListTile(
                      title: Text(it["caption"]),
                      onTap: () {
                        Navigator.pop(context);
                        Navigator.pushReplacementNamed(
                          context,
                          ROUTES.MEMES.route,
                          arguments: MemesScreenArgs(topicId: it["id"]),
                        );
                      },
                    ))),
                Divider(),
                Container(
                  padding: const EdgeInsets.only(left: 10),
                  child: Text(
                    "Providers",
                    style: TextStyle(fontSize: 18),
                  ),
                ),
                ...(_providers.map((it) => ListTile(
                      leading: ProviderLogo(providerId: it),
                      title: Text((it as String).capitalize()),
                      onTap: () {
                        Navigator.pop(context);
                        Navigator.pushReplacementNamed(
                          context,
                          ROUTES.MEMES.route,
                          arguments: MemesScreenArgs(providerId: it),
                        );
                      },
                    ))),
                Divider(),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
