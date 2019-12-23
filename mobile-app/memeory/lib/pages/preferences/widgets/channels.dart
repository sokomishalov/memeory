import 'package:flutter/material.dart';
import 'package:memeory/api/channels.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/components/containers/future_builder.dart';
import 'package:memeory/components/images/channel_logo.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/util/routes/routes.dart';

class ChannelPreferences extends StatefulWidget {
  @override
  _ChannelPreferencesState createState() => _ChannelPreferencesState();
}

class _ChannelPreferencesState extends State<ChannelPreferences> {
  Future<List> _channels;

  @override
  void initState() {
    _channels = fetchChannels();
    super.initState();
  }

  Future<void> onTapChannel(String id) async {}

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Column(
        children: [
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

                    return GestureDetector(
                      onTap: () async {
                        Navigator.pushReplacementNamed(
                          context,
                          ROUTES.MEMES.route,
                          arguments: MemesScreenArgs(
                            scrollingAxis: await getPreferredScrollingAxis(),
                            channelId: id,
                          ),
                        );
                      },
                      child: GridTile(
                        child: Card(
                          color: CardTheme.of(context).color,
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
