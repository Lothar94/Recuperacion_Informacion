#!/usr/bin/env ruby
#encoding: utf-8

require 'csv'

def selector

  data = CSV.read("Cortado.csv")
  modified_data = []
  data.each do |row|
    modified_data << [row[0], row[1], row[2], row[3], row[7], row[8], row[12], row[13], row[14], row[15], row[16], row[17], row[18]]
  end

  CSV.open("Modified.csv", "wb") do |csv|
    modified_data.each do |row|
      csv << row
    end
  end
end

if __FILE__ == $0
  selector
end
