import 'package:memeory/model/channel.dart';
import 'package:memeory/model/topic.dart';
import 'package:memeory/store/actions/actions.dart';
import 'package:memeory/store/state/app_state.dart';
import 'package:redux/redux.dart';

AppState appReducer(AppState state, action) {
  return AppState(
    providersReducer(state.providers, action),
    topicsReducer(state.topics, action),
    channelsReducer(state.channels, action),
  );
}

final Reducer<List<String>> providersReducer = combineReducers([
  TypedReducer<List<String>, ProvidersReceivedAction>(_receivedProviders),
]);

final Reducer<List<Topic>> topicsReducer = combineReducers([
  TypedReducer<List<Topic>, TopicsReceivedAction>(_receivedTopics),
]);

final Reducer<List<Channel>> channelsReducer = combineReducers([
  TypedReducer<List<Channel>, ChannelsReceivedAction>(_receivedChannels),
]);

List<Topic> _receivedTopics(
  List<Topic> items,
  TopicsReceivedAction action,
) {
  return List.unmodifiable(action.topics);
}

List<String> _receivedProviders(
  List<String> items,
  ProvidersReceivedAction action,
) {
  return List.unmodifiable(action.providers);
}

List<Channel> _receivedChannels(
  List<Channel> state,
  ChannelsReceivedAction action,
) {
  return List.unmodifiable(action.channels);
}
