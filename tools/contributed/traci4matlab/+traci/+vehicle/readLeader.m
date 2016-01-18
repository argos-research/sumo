function [vehID, dist] = readLeader(result)
result.readInt();
result.read(1);
vehID = result.readString();
result.read(1);
dist = result.readDouble();




