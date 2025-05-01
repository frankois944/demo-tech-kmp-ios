//
//  DestinationCell.swift
//  DestinationGuide
//
//  Created by Francois Dabonot on 01/05/2025.
//

import UIKit
import Shared

class DestinationCell: UICollectionViewCell {

    // MARK: Propeties

    static let identifier = "DestinationCell"

    var dataTask: URLSessionDataTask?

    // MARK: - Components

    let labelDestination: UILabel = {
        let lbl = UILabel()
        lbl.numberOfLines = 0
        lbl.text = "Asie"
        lbl.font = UIFont.avertaExtraBold(fontSize: 38)
        lbl.textColor = .white
        lbl.translatesAutoresizingMaskIntoConstraints = false
        return lbl
    }()

    //  Bouton plutôt qu'un label pour pouvoir mettre un padding
    //  Evite d'imbriquer un label dans une UIView

    let labelTag: UIButton = {
        let lbl = UIButton()
        lbl.setTitle("Test", for: .normal)
        lbl.setTitleColor(.white, for: .normal)
        lbl.contentEdgeInsets = UIEdgeInsets(top: 3, left: 8, bottom: 3, right: 8)
        lbl.titleLabel?.font = UIFont.avertaBold(fontSize: 16)
        lbl.isUserInteractionEnabled = false
        lbl.backgroundColor = UIColor.evaneos(color: .ink)
        lbl.layer.cornerRadius = 5
        lbl.translatesAutoresizingMaskIntoConstraints = false
        return lbl
    }()

    let stackViewRating: UIStackView = {
        let stackView = UIStackView(frame: CGRect(x: 0, y: 0, width: 100, height: 30))
        stackView.axis = .horizontal
        stackView.distribution = .fillEqually
        stackView.alignment = .fill
        stackView.spacing = 0
        stackView.translatesAutoresizingMaskIntoConstraints = false
        return stackView
    }()

    let imageViewBackground: UIImageView = {
        let imageView = UIImageView()
        imageView.layer.cornerRadius = 16
        imageView.layer.masksToBounds = true
        return imageView
    }()

    lazy var gradientView: GradientView = {
        let gradientView = GradientView()
        gradientView.startColor = .black.withAlphaComponent(0)
        gradientView.endColor = .black.withAlphaComponent(0.5)

        return gradientView
    }()

    // MARK: - Init

    override init(frame: CGRect) {
        super.init(frame: frame)

        self.backgroundColor = .clear

        self.shadow()
        self.addView()
    }

    override func layoutSubviews() {
        super.layoutSubviews()

        self.gradientView.frame = imageViewBackground.frame

        self.imageViewBackground.addSubview(gradientView)
        self.backgroundView = self.imageViewBackground
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    // MARK: - Override func

    override func prepareForReuse() {
        super.prepareForReuse()
        self.stackViewRating.arrangedSubviews.forEach {
            NSLayoutConstraint.deactivate($0.constraints)
            $0.removeFromSuperview()
        }
        self.labelDestination.text = nil

        self.dataTask?.cancel()
    }

    func downloadImage(url: URL) {
        self.dataTask?.resume()

        self.dataTask = URLSession.shared.dataTask(with: URLRequest(url: url), completionHandler: { data, _, _  in
            if let data = data {
                DispatchQueue.main.async {
                    self.imageViewBackground.image = UIImage(data: data)
                }
            }
        })

        self.dataTask?.resume()
    }

    // MARK: - Function

    func setupCell(destination: Destination) {
        self.labelDestination.text = destination.name
        self.configureStackView(rating: Int(destination.rating))
        self.labelTag.setTitle(destination.tag, for: .normal)

        self.downloadImage(url: URL(string: destination.picture)!)

        self.dataTask?.resume()
    }

    private func shadow() {
        layer.backgroundColor = UIColor.clear.cgColor
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOffset = CGSize(width: 0, height: 4.0)
        layer.shadowOpacity = 0.25
        layer.shadowRadius = 4.0
    }

    private func addView() {

        self.addSubview(labelTag)
        self.addSubview(labelDestination)
        self.addSubview(stackViewRating)

        self.constraintInit()
    }

    private func configureStackView(rating: Int) {
        var stars = [UIImageView]()

        for i in 0..<5 {
            let imageView = UIImageView()
            imageView.tintColor = UIColor.evaneos(color: .gold)
            if i < rating {
                let image = UIImage(systemName: "star.fill")
                imageView.image = image
            } else {
                let image = UIImage(systemName: "star")
                imageView.image = image
            }
            stars.append(imageView)
        }

        for star in stars {
            self.stackViewRating.addArrangedSubview(star)
        }
    }

    private func constraintInit() {
        NSLayoutConstraint.activate([
            self.labelTag.leftAnchor.constraint(equalTo: self.leftAnchor, constant: 16),
            self.labelTag.leftAnchor.constraint(equalTo: self.leftAnchor, constant: 16),
            self.labelTag.bottomAnchor.constraint(equalTo: self.bottomAnchor, constant: -16),

            self.stackViewRating.bottomAnchor.constraint(equalTo: self.labelTag.topAnchor, constant: -8),
            self.stackViewRating.leftAnchor.constraint(equalTo: self.leftAnchor, constant: 16),

            self.labelDestination.bottomAnchor.constraint(equalTo: self.stackViewRating.topAnchor, constant: 0),
            self.labelDestination.leftAnchor.constraint(equalTo: self.leftAnchor, constant: 16),
            self.labelDestination.rightAnchor.constraint(equalTo: self.rightAnchor, constant: 16)
        ])
    }

}
