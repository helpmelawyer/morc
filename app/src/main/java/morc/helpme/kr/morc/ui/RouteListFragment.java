package morc.helpme.kr.morc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import morc.helpme.kr.morc.R;
import morc.helpme.kr.morc.model.RouteInfo;


public class RouteListFragment extends Fragment implements RouteItemClicekListner {

  @BindView(R.id.recyclerview) RecyclerView recyclerView;
  private RouteInfoAdapter routeInfoAdapter;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_route_list, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    setupUI();
  }

  private void setupUI() {
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    Realm realm = Realm.getDefaultInstance();

    RealmResults<RouteInfo> routeInfoRealmResults = realm.where(RouteInfo.class).findAll();
    routeInfoAdapter = new RouteInfoAdapter(routeInfoRealmResults);
    routeInfoRealmResults.addChangeListener(new RealmChangeListener<RealmResults<RouteInfo>>() {
      @Override public void onChange(RealmResults<RouteInfo> element) {
        routeInfoAdapter.notifyDataSetChanged();
      }
    });

    routeInfoAdapter.setRouteItemClicekListner(this);
    recyclerView.setAdapter(routeInfoAdapter);

  }

  @OnClick(R.id.fab) void onClickFAB() {
    startActivity(new Intent(getActivity(), RouteActivity.class));
  }

  @Override public void onClickRouteInfo(int position, RouteInfo routeInfo) {
    Intent intent = new Intent(getActivity(), RouteActivity.class);
    intent.putExtra("Route_id", routeInfo.id);
    startActivity(intent);
  }
}
