import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:memeory/components/images/provider_logo.dart';
import 'package:memeory/model/topic.dart';
import 'package:memeory/pages/drawer/drawer_header.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/store/state/app_state.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:memeory/util/strings/strings.dart';
import 'package:redux/redux.dart';

class CustomDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Column(
        children: <Widget>[
          MemeoryDrawerHeader(),
          Expanded(
            child: ListView(
              shrinkWrap: true,
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
                StoreConnector<AppState, List<Topic>>(
                  converter: (Store<AppState> store) => store.state.topics,
                  builder: (BuildContext context, List<Topic> topics) {
                    return Column(
                      children: topics
                          .map((it) => ListTile(
                                title: Text(it.caption),
                                onTap: () {
                                  Navigator.pop(context);
                                  Navigator.pushReplacementNamed(
                                    context,
                                    ROUTES.MEMES.route,
                                    arguments: MemesScreenArgs(topicId: it.id),
                                  );
                                },
                              ))
                          .toList(),
                    );
                  },
                ),
                Divider(),
                Container(
                  padding: const EdgeInsets.only(left: 10),
                  child: Text(
                    "Providers",
                    style: TextStyle(fontSize: 18),
                  ),
                ),
                StoreConnector<AppState, List<String>>(
                  converter: (Store<AppState> store) => store.state.providers,
                  builder: (BuildContext context, List<String> providers) {
                    return Column(
                      children: providers
                          .map((it) => ListTile(
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
                              ))
                          .toList(),
                    );
                  },
                ),
                Divider(),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
