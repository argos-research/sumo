function switchConnection(label)
%switchConnection Switch to another connection with SUMO.
%
%   switchConnection(label) Switch to the connection specified in LABEL 
%   with the SUMO server.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: switchConnection.m 20 2015-03-02 16:52:32Z afacostag $

global connections
connections('') = connections(label);