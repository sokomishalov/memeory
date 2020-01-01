import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:memeory/components/images/channel_logo.dart';
import 'package:memeory/components/images/provider_logo.dart';
import 'package:memeory/model/channel.dart';
import 'package:memeory/model/topic.dart';
import 'package:memeory/pages/memes/memes_screen_args.dart';
import 'package:memeory/store/state/app_state.dart';
import 'package:memeory/util/routes/routes.dart';
import 'package:memeory/util/strings/strings.dart';
import 'package:redux/redux.dart';

class ChannelPreferences extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: StoreConnector<AppState, List<Channel>>(
        converter: (Store<AppState> store) => store.state.channels,
        builder: (BuildContext context, List<Channel> channels) {
          return GridView.builder(
            padding: EdgeInsets.symmetric(horizontal: 5),
            shrinkWrap: true,
            itemCount: channels.length,
            gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
              maxCrossAxisExtent: 180,
            ),
            itemBuilder: (BuildContext context, int index) {
              var item = channels[index];
              var id = item.id;

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
                          padding: EdgeInsets.only(top: 5),
                          child: ChannelLogo(
                            channelId: id,
                          ),
                        ),
                        Container(
                          padding: EdgeInsets.only(top: 7),
                          child: Text(
                            item.name ?? id,
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
                                  providerId: item.provider,
                                ),
                              ),
                              Container(
                                margin: const EdgeInsets.only(left: 5),
                                child: Text(
                                  item.provider.toString().capitalize(),
                                  style: TextStyle(
                                    fontSize: 12,
                                    color: Theme.of(context)
                                        .textTheme
                                        .title
                                        .color
                                        .withOpacity(0.6),
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                        StoreConnector<AppState, List<Topic>>(
                          converter: (Store<AppState> store) => store.state.topics,
                          builder: (BuildContext context, List<Topic> topics) {
                            return Container(
                              padding: const EdgeInsets.only(top: 2),
                              child: Container(
                                margin: const EdgeInsets.only(left: 5),
                                child: Text(
                                  topics
                                      .where(
                                          (it) => item.topics.contains(it.id))
                                      .map((it) => it.caption)
                                      .join(','),
                                  softWrap: true,
                                  maxLines: 1,
                                  overflow: TextOverflow.ellipsis,
                                  style: TextStyle(
                                    fontSize: 12,
                                    color: Theme.of(context)
                                        .textTheme
                                        .title
                                        .color
                                        .withOpacity(0.6),
                                  ),
                                ),
                              ),
                            );
                          },
                        ),
                      ],
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
