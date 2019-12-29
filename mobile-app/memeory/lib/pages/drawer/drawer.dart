import 'package:flutter/material.dart';
import 'package:memeory/api/providers.dart';
import 'package:memeory/api/topics.dart';
import 'package:memeory/components/images/provider_logo.dart';
import 'package:memeory/model/topic.dart';
import 'package:memeory/pages/drawer/drawer_header.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:memeory/util/strings/strings.dart';

class CustomDrawer extends StatefulWidget {
  @override
  _CustomDrawerState createState() => _CustomDrawerState();
}

class _CustomDrawerState extends State<CustomDrawer> {
  List<Topic> _topics;
  List<String> _providers;

  @override
  void initState() {
    _topics = [];
    _providers = [];

    super.initState();

    initData();
  }

  initData() async {
    final topics = await fetchTopics();
    final providers = await fetchProviders();
    setState(() {
      _topics = topics;
      _providers = providers;
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
                      title: Text(it.caption),
                      onTap: () {
                        Navigator.pop(context);
                        Navigator.pushReplacementNamed(
                          context,
                          ROUTES.MEMES.route,
                          arguments: MemesScreenArgs(topicId: it.id),
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
                      title: Text(it.toString().capitalize()),
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
