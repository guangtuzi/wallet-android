/*
 * Copyright 2013, 2014 Megion Research and Development GmbH
 *
 * Licensed under the Microsoft Reference Source License (MS-RSL)
 *
 * This license governs use of the accompanying software. If you use the software, you accept this license.
 * If you do not accept the license, do not use the software.
 *
 * 1. Definitions
 * The terms "reproduce," "reproduction," and "distribution" have the same meaning here as under U.S. copyright law.
 * "You" means the licensee of the software.
 * "Your company" means the company you worked for when you downloaded the software.
 * "Reference use" means use of the software within your company as a reference, in read only form, for the sole purposes
 * of debugging your products, maintaining your products, or enhancing the interoperability of your products with the
 * software, and specifically excludes the right to distribute the software outside of your company.
 * "Licensed patents" means any Licensor patent claims which read directly on the software as distributed by the Licensor
 * under this license.
 *
 * 2. Grant of Rights
 * (A) Copyright Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free copyright license to reproduce the software for reference use.
 * (B) Patent Grant- Subject to the terms of this license, the Licensor grants you a non-transferable, non-exclusive,
 * worldwide, royalty-free patent license under licensed patents for reference use.
 *
 * 3. Limitations
 * (A) No Trademark License- This license does not grant you any rights to use the Licensor’s name, logo, or trademarks.
 * (B) If you begin patent litigation against the Licensor over patents that you think may apply to the software
 * (including a cross-claim or counterclaim in a lawsuit), your license to the software ends automatically.
 * (C) The software is licensed "as-is." You bear the risk of using it. The Licensor gives no express warranties,
 * guarantees or conditions. You may have additional consumer rights under your local laws which this license cannot
 * change. To the extent permitted under your local laws, the Licensor excludes the implied warranties of merchantability,
 * fitness for a particular purpose and non-infringement.
 */

package com.mycelium.wallet.activity.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mycelium.wallet.R;
import com.mycelium.wallet.activity.util.PartnerInfo;
import com.mycelium.wallet.activity.util.RecommendationAdapter;

import java.util.ArrayList;


public class RecommendationsFragment extends Fragment {
    private View _root;

    ListView recommendationsList;
    TextView moreInformation;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        _root = inflater.inflate(R.layout.main_recommendations_view, container, false);

        recommendationsList = (ListView) _root.findViewById(R.id.list);
        ArrayList list = new ArrayList<PartnerInfo>();

        list.add(new PartnerInfo(getString(R.string.partner_ledger),
                getString(R.string.partner_ledger_short),
                getString(R.string.partner_ledger_info),
                getString(R.string.partner_ledger_url), R.drawable.ledger_icon));

        list.add(new PartnerInfo(getString(R.string.partner_trezor),
                                 getString(R.string.partner_trezor_short),
                                 getString(R.string.partner_trezor_info),
                                 getString(R.string.partner_trezor_url), R.drawable.trezor2));

        list.add(new PartnerInfo(getString(R.string.partner_purse),
                getString(R.string.partner_purse_short),
                getString(R.string.partner_purse_info),
                getString(R.string.partner_purse_url), R.drawable.purse_small));

        list.add(new PartnerInfo(getString(R.string.partner_coinbase),
                getString(R.string.partner_coinbase_short),
                getString(R.string.partner_coinbase_info),
                getString(R.string.partner_coinbase_url), R.drawable.coinbase));

        list.add(new PartnerInfo(getString(R.string.partner_hashing24),
                getString(R.string.partner_hashing24_short),
                getString(R.string.partner_hashing24_info),
                getString(R.string.partner_hashing24_url), R.drawable.hashing24));

        //View footerView = ((LayoutInflater) ActivityContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        View footerView = getActivity().getLayoutInflater().inflate(R.layout.main_recommendations_list_footer, null, false);
        recommendationsList.addFooterView(footerView);
        moreInformation = (TextView) footerView.findViewById(R.id.tvMoreInformation);
        moreInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View custom = LayoutInflater.from(view.getContext())
                        .inflate(R.layout.main_recomendation_dialog_view, null, false);
                TextView part1 = (TextView) custom.findViewById(R.id.part1);
                part1.setText(R.string.partner_more_info_text_part1);

                TextView part2 = (TextView) custom.findViewById(R.id.part2);
                part2.setText(R.string.partner_more_info_text_part2);

                ((ImageView) custom.findViewById(R.id.image)).setImageResource(R.drawable.mycelium_logo_transp);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.partner_more_info);
//                builder.setMessage(R.string.partner_more_info_text);
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialogInterface, int i) { }
                        } );
//                builder.setIcon(R.drawable.mycelium_logo_transp);
                builder.setView(custom);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        RecommendationAdapter adapter = new RecommendationAdapter(getActivity(), R.layout.main_recommendations_list_item, list);
        recommendationsList.setAdapter(adapter);

        adapter.setClickListener(new RecommendationAdapter.ClickListener() {
            @Override
            public void itemClick(final PartnerInfo bean) {
                if (bean.getInfo() != null && bean.getInfo().length() > 0) {
                    View custom = LayoutInflater.from(getActivity())
                            .inflate(R.layout.main_recomendation_dialog_view, null, false);
                    TextView part1 = (TextView) custom.findViewById(R.id.part1);
                    int pointIndex = bean.getInfo().indexOf(".") + 1;
                    part1.setText(bean.getInfo().substring(0, pointIndex));

                    TextView part2 = (TextView) custom.findViewById(R.id.part2);
                    part2.setText(bean.getInfo().substring(pointIndex));

                    ((ImageView) custom.findViewById(R.id.image)).setImageResource(bean.getIcon());

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setMessage(bean.getInfo());
                    builder.setTitle(R.string.warning_partner);
//                        builder.setIcon(bean.getIcon());
                    builder.setView(custom);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (bean.getUri() != null) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(bean.getUri()));
                                startActivity(intent);
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    if (bean.getUri() != null) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(bean.getUri().toString()));
                        startActivity(i);
                    }
                }
            }
        });

        return _root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
