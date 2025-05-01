//
//  DestinationsViewController.swift
//  DestinationGuide
//
//  Created by Alexandre Guibert1 on 02/08/2021.
//

import UIKit
import Shared
import Combine

class DestinationsViewController: UIViewController  {
    
    // MARK: - Properties
    
    var disposebag = Set<AnyCancellable>()
    let viewModel = SharedViewModel<DestinationsViewModel>(.init(destinationFetchingService: DestinationFetchingServiceImpl(), destinationHistoryService: DestinationHistoryServiceImpl()))
    var destinations = [Destination]()
    var recentDestinations = [DestinationDetails]()
    
    // MARK: - Components
    
    lazy var collectionViewLayout: UICollectionViewLayout = {
        // this layout is not responsive
        let layout: UICollectionViewFlowLayout = UICollectionViewFlowLayout()
        layout.sectionInset = UIEdgeInsets(top: 16, left: 0, bottom: 32, right: 0)
        layout.minimumLineSpacing = 32
        layout.itemSize = .init(width: 342, height: 280)
        return layout
    }()
    
    lazy var collectionView: UICollectionView = {
        let collectionView = UICollectionView(frame: self.view.frame, collectionViewLayout: self.collectionViewLayout)
        collectionView.translatesAutoresizingMaskIntoConstraints = false
        collectionView.dataSource = self
        collectionView.delegate = self
        collectionView.register(DestinationCell.self, forCellWithReuseIdentifier: DestinationCell.identifier)
        collectionView.register(HistoryCell.self, forCellWithReuseIdentifier: HistoryCell.identifier)
        collectionView.register(SectionHeader.self, forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader, withReuseIdentifier: SectionHeader.identifier)
        collectionView.backgroundColor = .clear
        collectionView.showsVerticalScrollIndicator = false
        return collectionView
    }()

    // MARK: - Init
    
    override func viewDidLoad() {
        super.viewDidLoad()
        bindView()
        
        view.backgroundColor = .white
        view.addSubview(collectionView)
        collectionView.frame = view.frame
        
        collectionView.dataSource = self
    }
    
    func bindView() {
        // async loading the destination
        viewModel.instance.destinations
            .toPublisher()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] destinations in
                if (destinations != self?.destinations) {
                    self?.destinations = destinations
                    self?.collectionView.reloadSections(.init(arrayLiteral: 1))
                }
            }.store(in: &disposebag)
        
        // display error if needed
        viewModel.instance.error
            .toPublisher()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] error in
                if let error {
                    let alert = UIAlertController(title: "Erreur", message: error, preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Annuler", style: .cancel))
                    self?.present(alert, animated: true)
                }
            }.store(in: &disposebag)
        
        // observice history change
        viewModel.instance.history
            .toPublisher()
            .receive(on: DispatchQueue.main)
            .sink { [weak self] history in
                if (history != self?.recentDestinations) {
                    self?.recentDestinations = history
                    self?.collectionView.reloadSections(.init(arrayLiteral: 0))
                }
            }.store(in: &disposebag)
    }
    
    func onSelectDestination(id: String) {
        Task {
            do {
                let data = try await viewModel.instance.onSelectDestination(destinationId: id)
                //TODO: move the navigation to a coordinator
                self.navigationController?.pushViewController(DestinationDetailsController(
                    title: data.name,
                    webviewUrl: URL(string: data.url)!
                ), animated: true)
            } catch {
                let alert = UIAlertController(title: "Erreur", message: error.localizedDescription, preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "Annuler", style: .cancel))
                self.present(alert, animated: true)
            }
        }
    }
}
