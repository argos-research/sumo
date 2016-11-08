function nextSwitch = getNextSwitch(tlsID)
%getNextSwitch Returns the time in ms at which the next phase change will
%be performed.
%   nextSwitch = getNextSwitch(TLSID) Returns the time in ms at which the
%   next phase change will be performed. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getNextSwitch.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
nextSwitch = traci.trafficlights.getUniversal(constants.TL_NEXT_SWITCH, tlsID);