//
//  UICollectionReusableView.swift
//  DestinationGuide
//
//  Created by Francois Dabonot on 01/05/2025.
//

import UIKit

class SectionHeader: UICollectionReusableView {
    
    // MARK: - Properties
    
    static let identifier = "SectionHeader"
    static let titleText = "Toutes nos destinations"
    static let titleFont = UIFont.avertaBold(fontSize: 28)
    static let horizontalPadding: CGFloat = 16
    static let verticalPadding: CGFloat = 8
    
    // MARK: - Components
    
    lazy var titleLabel: UILabel = {
        let label = UILabel()
        label.font = Self.titleFont
        label.text = Self.titleText
        return label
    }()
    
    // MARK: - Init
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        layoutMargins = .init(top: Self.verticalPadding,
                              left: Self.horizontalPadding,
                              bottom: Self.verticalPadding,
                              right: Self.horizontalPadding)
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        addSubview(titleLabel)
        
        NSLayoutConstraint.activate([
            self.layoutMarginsGuide.leadingAnchor.constraint(equalTo: titleLabel.leadingAnchor),
            self.layoutMarginsGuide.trailingAnchor.constraint(equalTo: titleLabel.trailingAnchor),
            self.layoutMarginsGuide.bottomAnchor.constraint(equalTo: titleLabel.bottomAnchor),
            self.layoutMarginsGuide.topAnchor.constraint(equalTo: titleLabel.topAnchor),
        ])
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    static func size(forWidth width: CGFloat) -> CGSize {
        let availableWidth = width - (horizontalPadding * 2)
        let size = titleText.size(withAttributes: [.font: titleFont])
        let height = size.height + (verticalPadding * 2)
        return CGSize(width: width, height: height)
    }
}
