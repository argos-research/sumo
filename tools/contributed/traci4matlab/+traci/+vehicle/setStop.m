function setStop(vehID, edgeID, varargin)
%setStop Set a stop for the vehicle.
%   setStop(VEHID,EDGEID) Sets a stop for the vehicle VEHID in the edge
%   EDGEID for the maximum allowed time. The position and the lane index
%   default to one and zero respectively.
%   setStop(...,POS) Specify the position of the stop in the lane.
%   setStop(...,LANEINDEX) Specify the lane index in which the stop will be
%   made.
%   setStop(...,DURATION) Specify the duration of the stop.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setStop.m 31 2016-09-28 15:16:56Z afacostag $


import traci.constants
global message

p = inputParser;
p.FunctionName = 'vehicle.setStop';
p.addRequired('vehID',@ischar)
p.addRequired('edgeID',@ischar)
p.addOptional('pos', 1, @isnumeric)   % -3 = DEPART_NOW
p.addOptional('laneIndex', 0, @isnumeric)
p.addOptional('duration', 2^31-1, @isnumeric)
p.addOptional('flags', 0, @isnumeric)
p.addOptional('startPos', constants.INVALID_DOUBLE_VALUE, @isnumeric)
p.addOptional('until', -1, @isnumeric)
p.parse(vehID, edgeID, varargin{:})

vehID = p.Results.vehID;
edgeID = p.Results.edgeID;
pos = p.Results.pos;
laneIndex = p.Results.laneIndex;
duration= p.Results.duration;
flags = p.Results.flags;
startPos = p.Results.startPos;
until = p.Results.until;


% if nargin < 8
%     until = -1;
%     if nargin < 7
%         startPos = constants.INVALID_DOUBLE_VALUE;
%         if nargin < 6
%             flags = 0;
%             if nargin < 5 
%                 duration = 2^31-1;
%                 if nargin < 4
%                     laneIndex = 0;
%                     if nargin < 3
%                         pos = 1;
%                     end
%                 end
%             end
%         end
%     end
% end

traci.beginMessage(constants.CMD_SET_VEHICLE_VARIABLE, constants.CMD_STOP,...
    vehID, 1+4+1+4+length(edgeID)+1+8+1+1+1+4+1+1+1+8+1+4);
message.string = [message.string uint8(sscanf(constants.TYPE_COMPOUND,'%x')) ...
    traci.packInt32(7)];
message.string = [message.string uint8(sscanf(constants.TYPE_STRING,'%x')) ...
    traci.packInt32(length(edgeID)) uint8(edgeID)];
message.string = [message.string uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(pos) uint8([sscanf(constants.TYPE_BYTE,'%x') ...
    laneIndex sscanf(constants.TYPE_INTEGER,'%x')]) ...
    traci.packInt32(duration) sscanf(constants.TYPE_BYTE,'%x') flags];
message.string = [message.string uint8(sscanf(constants.TYPE_DOUBLE,'%x')) ...
    traci.packInt64(startPos) sscanf(constants.TYPE_INTEGER,'%x') traci.packInt32(until)];
traci.sendExact();