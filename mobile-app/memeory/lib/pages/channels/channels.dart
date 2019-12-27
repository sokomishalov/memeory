import 'package:flutter/material.dart';
import 'package:memeory/api/channels.dart';
import 'package:memeory/components/containers/future_builder.dart';
import 'package:memeory/components/images/channel_logo.dart';
import 'package:memeory/components/images/provider_logo.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:memeory/util/strings/strings.dart';

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

  @override
  Widget build(BuildContext context) {
    final textColorWithOpacity =
        Theme.of(context).textTheme.title.color.withOpacity(0.6);

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
                  gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                    maxCrossAxisExtent: 180,
                  ),
                  itemBuilder: (BuildContext context, int index) {
                    var item = data[index];
                    var id = item["id"];
                    var name = item["name"] ?? id;

                    return GestureDetector(
                      onTap: () {
                        Navigator.pushReplacementNamed(
                          context,
                          ROUTES.MEMES.route,
                          arguments: MemesScreenArgs(
                            channelId: id,
                          ),
                        );
                      },
                      child: GridTile(
                        child: Card(
                          color: CardTheme.of(context).color,
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              Container(
                                padding: EdgeInsets.only(top: 10),
                                child: ChannelLogo(
                                  channelId: item["id"],
                                ),
                              ),
                              Container(
                                padding: EdgeInsets.only(top: 10),
                                child: Text(
                                  name,
                                  softWrap: true,
                                  maxLines: 1,
                                  overflow: TextOverflow.ellipsis,
                                  textAlign: TextAlign.center,
                                  style: TextStyle(),
                                ),
                              ),
                              Container(
                                padding: EdgeInsets.only(top: 5),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: <Widget>[
                                    Container(
                                      child: ProviderLogo(
                                        size: 15,
                                        providerId: item["provider"],
                                      ),
                                    ),
                                    Container(
                                      margin: const EdgeInsets.only(left: 5),
                                      child: Text(
                                        item["provider"]
                                            .toString()
                                            .capitalize(),
                                        style: TextStyle(
                                          fontSize: 13,
                                          color: textColorWithOpacity,
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                              Container(
                                padding: EdgeInsets.only(top: 5),
                                child: Container(
                                  margin: const EdgeInsets.only(left: 5),
                                  child: Text(
                                    item["topics"].join(','),
                                    softWrap: true,
                                    maxLines: 1,
                                    overflow: TextOverflow.ellipsis,
                                    style: TextStyle(
                                      fontSize: 13,
                                      color: textColorWithOpacity,
                                    ),
                                  ),
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
