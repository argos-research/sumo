function IDCount = getIDCount()
%getIDCount Get the number of persons in the SUMO network.  

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getIDCount.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
IDCount = traci.person.getUniversal(constants.ID_COUNT, '');