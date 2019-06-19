import 'package:flutter/material.dart';
import 'package:memeory/api/channels.dart';
import 'package:memeory/cache/repository/channels_repo.dart';
import 'package:memeory/common/containers/future_builder.dart';

class ChannelPreferences extends StatefulWidget {
  @override
  _ChannelPreferencesState createState() => _ChannelPreferencesState();
}

class _ChannelPreferencesState extends State<ChannelPreferences> {
  Future<List> _channels;
  List _selectedChannels;

  @override
  void initState() {
    _channels = fetchChannels();

    super.initState();

    getSelectedChannels().then((channels) {
      setState(() {
        _selectedChannels = channels;
      });
    });
  }

  onTapChannel(String id) async {
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
  }

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: FutureWidget(
        future: _channels,
        render: (data) {
          return GridView.builder(
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
                    color: isActive
                        ? Colors.greenAccent.shade200
                        : CardTheme.of(context).color,
                    child: new Center(
                      child: Text(name),
                    ),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
