function controlledLanes = getControlledLanes(tlsID)
%getControlledLanes Get the controlled lanes.
%   controlledLanes = getControlledLanes(TLSID) Returns a cell array of 
%   strings containing the ids of the lanes controlled by the given traffic
%   lights. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getControlledLanes.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
controlledLanes = traci.trafficlights.getUniversal(constants.TL_CONTROLLED_LANES, tlsID);