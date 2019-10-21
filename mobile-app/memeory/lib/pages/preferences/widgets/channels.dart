import 'package:flutter/material.dart';
import 'package:memeory/api/channels.dart';
import 'package:memeory/api/profile.dart';
import 'package:memeory/cache/repository/channels_repo.dart';
import 'package:memeory/components/containers/future_builder.dart';
import 'package:memeory/components/images/channel_logo.dart';
import 'package:memeory/util/i18n/i18n.dart';

class ChannelPreferences extends StatefulWidget {
  @override
  _ChannelPreferencesState createState() => _ChannelPreferencesState();
}

class _ChannelPreferencesState extends State<ChannelPreferences> {
  Future<List> _channels;
  List _selectedChannels;
  bool _watchAll;

  @override
  void initState() {
    _channels = fetchChannels();
    _watchAll = false;

    super.initState();

    getWatchAll().then((val) {
      setState(() {
        _watchAll = val;
      });
    });

    getSelectedChannels().then((channels) {
      setState(() {
        _selectedChannels = channels;
      });
    });
  }

  onTapChannel(String id) async {
    if (!_watchAll) {
      bool toRemove = _selectedChannels.contains(id);
      if (toRemove) {
        await removeChannel(id);
      } else {
        await addChannel(id);
      }
      var newSelectedChannels = await getSelectedChannels();

      setState(() {
        _selectedChannels = newSelectedChannels;
      });

      await saveProfile();
    }
  }

  onTapWatchAll(bool value) async {
    await setWatchAll(value);
    await removeAllChannels();

    setState(() {
      _watchAll = value;
      _selectedChannels = [];
    });

    await saveProfile();
  }

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Column(
        children: [
          Container(
            padding: EdgeInsets.only(top: 10),
            child: SwitchListTile(
                title: Text(t(context, "watch_all")),
                subtitle: Text(
                  t(context, "watch_all_subtitle"),
                  style: TextStyle(fontSize: 11),
                ),
                value: _watchAll,
                onChanged: onTapWatchAll),
          ),
          FutureWidget(
            future: _channels,
            render: (data) {
              return Expanded(
                child: GridView.builder(
                  padding: EdgeInsets.symmetric(horizontal: 5),
                  shrinkWrap: true,
                  itemCount: data.length,
                  gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                    crossAxisCount: 3,
                  ),
                  itemBuilder: (BuildContext context, int index) {
                    var item = data[index];
                    var id = item["id"];
                    var name = item["name"] ?? id;

                    var isActive = _selectedChannels.contains(id);

                    return GestureDetector(
                      onTap: () async => await onTapChannel(id),
                      child: GridTile(
                        child: Card(
                          color: _watchAll || isActive
                              ? ButtonTheme.of(context).colorScheme.primary
                              : CardTheme.of(context).color,
                          child: Column(
                            children: [
                              Container(
                                padding: EdgeInsets.only(top: 22),
                                child: ChannelLogo(
                                  channelId: item["id"],
                                ),
                              ),
                              Container(
                                padding: EdgeInsets.only(top: 10),
                                child: Text(
                                  name,
                                  softWrap: true,
                                  maxLines: 2,
                                  textAlign: TextAlign.center,
                                  style: TextStyle(),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    );
                  },
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}
