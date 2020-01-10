import 'dart:async';

import 'package:memeory/api/channels.dart';
import 'package:memeory/api/providers.dart';
import 'package:memeory/api/topics.dart';
import 'package:memeory/store/actions/actions.dart';
import 'package:memeory/store/state/app_state.dart';
import 'package:redux/redux.dart';

List<Middleware<AppState>> createStoreMiddleware() {
  return [
    TypedMiddleware<AppState, ProvidersFetchAction>(_fetchProviders),
    TypedMiddleware<AppState, TopicsFetchAction>(_fetchTopics),
    TypedMiddleware<AppState, ChannelsFetchAction>(_fetchChannels),
  ];
}

Future _fetchProviders(
  Store<AppState> store,
  ProvidersFetchAction action,
  NextDispatcher next,
) async {
  var list = await fetchProviders();
  next(ProvidersReceivedAction(list));
}

Future _fetchTopics(
  Store<AppState> store,
  TopicsFetchAction action,
  NextDispatcher next,
) async {
  var list = await fetchTopics();
  next(TopicsReceivedAction(list));
}

Future _fetchChannels(
  Store<AppState> store,
  ChannelsFetchAction action,
  NextDispatcher next,
) async {
  var list = await fetchChannels();
  next(ChannelsReceivedAction(list));
}
