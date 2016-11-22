function netBoundary = getNetBoundary()
%getNetBoundary Get the boundary box of the network.
%   netBoundary = getNetBoundary() Returns the coordinates of the 
%   lower-left and the upper-right points that define the boundaries of the
%   network.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getNetBoundary.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
netBoundary = traci.simulation.getUniversal(constants.VAR_NET_BOUNDING_BOX);