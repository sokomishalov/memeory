import 'package:memeory/store/reducers/reducers.dart';
import 'package:memeory/store/state/app_state.dart';
import 'package:redux/redux.dart';

import 'middleware/middleware.dart';

final Store<AppState> store = Store<AppState>(
  appReducer,
  initialState: AppState.initial(),
  middleware: createStoreMiddleware(),
);
