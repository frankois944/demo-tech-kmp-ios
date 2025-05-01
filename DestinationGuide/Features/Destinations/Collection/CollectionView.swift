//
//  CollectionView.swift
//  DestinationGuide
//
//  Created by Francois Dabonot on 01/05/2025.
//

import UIKit

// MARK: - UICollectionViewDataSource

extension DestinationsViewController: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        if indexPath.section == 0 {
            .init(width: view.frame.width, height: 40)
        } else {
            .init(width: 342, height: 280)
        }
    }

    func numberOfSections(in collectionView: UICollectionView) -> Int {
        2
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if section == 0 {
            1
        } else {
            destinations.count
        }
    }

    func collectionView(_ collectionView: UICollectionView,
                        cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if indexPath.section == 0 {
            if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: HistoryCell.identifier,
                                                             for: indexPath) as? HistoryCell {
                cell.setupCell(destinations: recentDestinations) { [weak self] in
                    self?.onSelectRecentDestination($0)
                }
                return cell
            }
            return UICollectionViewCell()
        } else {
            let desti = destinations[indexPath.item]
            if let cell = collectionView.dequeueReusableCell(withReuseIdentifier: DestinationCell.identifier,
                                                             for: indexPath) as? DestinationCell {
                cell.setupCell(destination: desti)
                return cell
            }
            return UICollectionViewCell()
        }
    }

    func collectionView(_ collectionView: UICollectionView,
                        viewForSupplementaryElementOfKind kind: String,
                        at indexPath: IndexPath) -> UICollectionReusableView {
        switch kind {
        case UICollectionView.elementKindSectionHeader:
            let headerView = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: SectionHeader.identifier, for: indexPath) as? SectionHeader
            if indexPath.section == 0 {
                headerView?.titleLabel.text = "Destination récentes"
            } else {
                headerView?.titleLabel.text = "Toutes nos destinations"
            }
            return headerView ?? UICollectionReusableView()
        default:
            assert(false, "Unexpected element kind")
        }
    }
}

// MARK: - UICollectionViewDelegate

extension DestinationsViewController: UICollectionViewDelegate {

    func collectionView(_ collectionView: UICollectionView,
                        didSelectItemAt indexPath: IndexPath) {
        guard indexPath.section == 1 else { return }
        let desti = destinations[indexPath.item]
        onSelectDestination(id: desti.id)
    }
}

// MARK: - UICollectionViewDelegateFlowLayout

extension DestinationsViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        referenceSizeForHeaderInSection section: Int) -> CGSize {
        return SectionHeader.size(forWidth: collectionView.frame.width)
    }
}
