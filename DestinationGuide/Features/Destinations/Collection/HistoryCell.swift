//
//  HistoryCell.swift
//  DestinationGuide
//
//  Created by Francois Dabonot on 01/05/2025.
//

import UIKit
import Shared

class HistoryCell: UICollectionViewCell {
    
    // MARK: - Properties
    
    static let identifier = "HistoryCell"
    private var onSelectDestination: ((DestinationDetails) -> Void)?
    private var items = [DestinationDetails]()
    private let itemSpacing: Double = 16
    
    // MARK: - Components
    
    let scrollView: UIScrollView = {
        let layout = UIScrollView()
        return layout
    }()
    
    // MARK: - Init
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        scrollView.frame = .init(origin: .zero, size: frame.size)
        self.addSubview(scrollView)
    }
    
    func setupCell(destinations: [DestinationDetails], onSelectDestination: @escaping (DestinationDetails) -> Void) {
        self.onSelectDestination = onSelectDestination
        // no usage of constraint, using the old way, the original!
        if (items != destinations) {
            items = destinations
            scrollView.subviews.forEach { $0.removeFromSuperview() }
            var posX = itemSpacing
            for i in 0..<destinations.count {
                let destination = items[i]
                let button = RecentDestinationButton()
                button.setTitle(destination.name, for: .normal)
                button.sizeToFit()
                button.frame = .init(origin: .init(x: posX, y: 0), size: button.frame.size)
                button.tag = i
                button.addTarget(self, action: #selector(onSelectDestinationTap), for: .touchUpInside)
                posX += button.frame.size.width + itemSpacing
                scrollView.addSubview(button)
            }
            scrollView.contentSize = .init(width: posX, height: scrollView.frame.size.height)
        }
    }
    
    @objc func onSelectDestinationTap(sender: UIButton) {
        let destination = items[sender.tag]
        onSelectDestination?(destination)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
